package unihelp.example.groupe.services;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import unihelp.example.groupe.client.UserClient;
import unihelp.example.groupe.dto.GroupMemberDTO;
import unihelp.example.groupe.dto.GroupeWithMembersDTO;
import unihelp.example.groupe.dto.UserDTO;
import unihelp.example.groupe.entities.*;
import unihelp.example.groupe.entities.Typerole;
import unihelp.example.groupe.repositories.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class IGroupeServiceImpl implements IGroupeService {

    private final IGroupeRepository groupeRepository;
    private final IChatRepository chatRepository;
    private final IMessageRepository messageRepository;
    private final IGroupMembershipRepository groupMembershipRepository;
    private final UserClient userClient;
    private final SimpMessagingTemplate messagingTemplate; // ✅ Ajout WebSocket push
    private final JoinRequestRepository joinRequestRepository;


    @Override
    public Groupe createGroup(String groupName, List<String> userNames, String createdBy) {
        if (groupName == null || groupName.isBlank()) {
            throw new IllegalArgumentException("Le nom du groupe est obligatoire.");
        }

        if (userNames == null || userNames.isEmpty()) {
            throw new IllegalArgumentException("La liste des utilisateurs ne doit pas être vide.");
        }

        if (!userNames.contains(createdBy)) {
            userNames.add(createdBy);
        }

        Groupe group = new Groupe();
        group.setGroupName(groupName);
        group.setCreatedBy(createdBy);

        Chat chat = new Chat();
        chatRepository.save(chat);
        group.setChat(chat);

        Groupe savedGroup = groupeRepository.save(group);

        for (String username : userNames) {
            UserDTO user = userClient.getUserByUsername(username);

            if (!groupMembershipRepository.existsByUserIdAndGroupe(user.getId(), savedGroup)) {
                GroupMembership membership = new GroupMembership();
                membership.setUserId(user.getId());
                membership.setGroupe(savedGroup);
                membership.setRole(username.equals(createdBy) ? Typerole.ADMIN :Typerole.MEMBER);
                membership.setJoinedAt(LocalDateTime.now());
                groupMembershipRepository.save(membership);
            }
        }

        return savedGroup;
    }
    @Override
    public List<Groupe> getAllGroups() {
        List<Groupe> groups = groupeRepository.findAll();
        for (Groupe g : groups) {
            if (g.getChat() != null && g.getChat().getMessageList() != null) {
                g.setMessageCount(g.getChat().getMessageList().size());
            } else {
                g.setMessageCount(0);
            }
        }
        return groups;
    }

    @Override
    public List<GroupMemberDTO> getGroupMembers(Long groupId) {
        Groupe group = groupeRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Groupe non trouvé"));
        List<GroupMembership> memberships = groupMembershipRepository.findByGroupe(group);
        return memberships.stream().map(m -> {
            UserDTO user = userClient.getUserById(m.getUserId());
            return new GroupMemberDTO(
                    user.getId(),
                    user.getUserName(),
                    m.getRole().name() // convert enum to string
            );
        }).toList();
    }

    @Override
    public Groupe renameGroup(Long groupId, String newName) {
        Groupe group = groupeRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Groupe non trouvé avec ID: " + groupId));

        group.setGroupName(newName); // Assure-toi que le champ s'appelle bien 'nom'
        return groupeRepository.save(group);
    }
    @Override
    public void leaveGroup(Long groupId, String username) {
        Groupe group = groupeRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Groupe non trouvé avec ID: " + groupId));

        UserDTO user = userClient.getUserByUsername(username);

        GroupMembership membership = groupMembershipRepository
                .findByUserIdAndGroupe(user.getId(), group)
                .orElseThrow(() -> new RuntimeException("L'utilisateur n'est pas membre de ce groupe"));

        // 🔥 Supprimer l'utilisateur du groupe
        groupMembershipRepository.delete(membership);

        // ✍️ Créer un message système
        Message systemMessage = new Message();
        systemMessage.setSenderName("🔔 Système");
        systemMessage.setContent("👤 " + user.getUserName() + " a quitté le groupe.");
        systemMessage.setChat(group.getChat());

        messageRepository.save(systemMessage);
        group.getChat().getMessageList().add(systemMessage);
        chatRepository.save(group.getChat());
    }


    @Override
    public List<Groupe> getGroupsForUser(String username) {
        UserDTO user = userClient.getUserByUsername(username);
        List<GroupMembership> memberships = groupMembershipRepository.findByUserId(user.getId());

        List<Groupe> groupes = memberships.stream()
                .map(GroupMembership::getGroupe)
                .toList();

        for (Groupe g : groupes) {
            if (g.getChat() != null && g.getChat().getMessageList() != null) {
                g.setMessageCount(g.getChat().getMessageList().size());
            } else {
                g.setMessageCount(0);
            }
        }

        return groupes;
    }


    @Override
    public Groupe addUserToGroup(Long groupId, String userName) {
        Groupe group = groupeRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Groupe non trouvé"));

        UserDTO user = userClient.getUserByUsername(userName);

        if (!groupMembershipRepository.existsByUserIdAndGroupe(user.getId(), group)) {
            GroupMembership membership = new GroupMembership();
            membership.setUserId(user.getId());
            membership.setGroupe(group);
            membership.setRole(Typerole.MEMBER);
            membership.setJoinedAt(LocalDateTime.now());
            groupMembershipRepository.save(membership);
        }

        return group;
    }


    @Override
    @Transactional
    public void requestToJoin(Long groupId, String username) {
        try {
            Groupe group = groupeRepository.findById(groupId)
                    .orElseThrow(() -> new RuntimeException("Groupe introuvable ID=" + groupId));

            UserDTO user = userClient.getUserByUsername(username);
            if (user == null) {
                throw new RuntimeException("Utilisateur non trouvé: " + username);
            }

            Long userId = user.getId();

            if (groupMembershipRepository.existsByUserIdAndGroupe(userId, group)) {
                throw new RuntimeException("Tu es déjà membre de ce groupe");
            }

            joinRequestRepository.deleteByUsernameAndGroupe(username, group);

            JoinRequest newRequest = new JoinRequest();
            newRequest.setUsername(username);
            newRequest.setGroupe(group);
            newRequest.setAccepted(false);
            newRequest.setRequestedAt(LocalDateTime.now());
            joinRequestRepository.save(newRequest);

            System.out.println("✅ Demande enregistrée pour " + username);
        } catch (Exception e) {
            System.err.println("❌ ERREUR DANS requestToJoin : " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erreur interne lors de la demande de rejoindre : " + e.getMessage());
        }
    }
    @Override
    public List<JoinRequest> getPendingRequests(Long groupId) {
        return joinRequestRepository.findByGroupeGroupIdAndAcceptedFalse(groupId);
    }
    @Override
    public void acceptJoinRequest(Long requestId) {
        JoinRequest req = joinRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Demande introuvable"));

        UserDTO user = userClient.getUserByUsername(req.getUsername());

        GroupMembership m = new GroupMembership();
        m.setUserId(user.getId());
        m.setGroupe(req.getGroupe());
        m.setRole(Typerole.MEMBER);
        m.setJoinedAt(LocalDateTime.now());

        groupMembershipRepository.save(m);

        req.setAccepted(true);
        joinRequestRepository.save(req);
    }

    @Override
    public Chat sendMessage(Long groupId, String userName, String messageText) {
        Groupe group = groupeRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Groupe non trouvé"));

        UserDTO sender = userClient.getUserByUsername(userName);

        Message message = new Message();
        message.setSenderName(sender.getUserName());
        message.setContent(messageText);
        message.setChat(group.getChat());

        messageRepository.save(message);

        Chat chat = group.getChat();
        chat.getMessageList().add(message);
        return chatRepository.save(chat);
    }
    @Override
    public List<Message> getMessages(Long groupId) {
        Groupe group = groupeRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Groupe non trouvé"));
        return group.getChat().getMessageList();
    }

    @Override
    public void notifyVideoCall(Long groupId, String username) {
        Groupe group = groupeRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Groupe non trouvé avec ID: " + groupId));

        UserDTO user = userClient.getUserByUsername(username);

        Message message = new Message();
        message.setSenderName("Système");
        message.setContent("📞 " + user.getUserName() + " a lancé un appel vidéo.");
        message.setChat(group.getChat());

        messageRepository.save(message);
    }

    @Override
    public void startVideoCall(Long groupId, String username) {
        Groupe group = groupeRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Groupe non trouvé avec ID: " + groupId));

        UserDTO user = userClient.getUserByUsername(username);

        String roomId = "group-" + groupId + "-call-" + System.currentTimeMillis();

        Message msg = new Message();
        msg.setSenderName("Système");
        msg.setContent("📞 " + user.getUserName() + " a lancé un appel vidéo. Rejoindre ici: " + roomId);
        msg.setRoomId(roomId); // 🔥 assure-toi que ce champ existe dans l'entité
        msg.setChat(group.getChat());

        messageRepository.save(msg);
    }
    @Override
    public void handleIncomingWebSocketMessage(Long groupId, Message message) {
        Groupe group = groupeRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Groupe non trouvé"));

        message.setChat(group.getChat());
        messageRepository.save(message);

        // ✅ Broadcast aux abonnés WebSocket
        messagingTemplate.convertAndSend("/topic/chat/" + groupId, message);
    }
    @Override
    public List<GroupeWithMembersDTO> getAllGroupsWithMembers() {
        List<Groupe> groups = groupeRepository.findAll();
        List<GroupeWithMembersDTO> result = new ArrayList<>();

        for (Groupe group : groups) {
            GroupeWithMembersDTO dto = new GroupeWithMembersDTO();
            dto.setGroupId(group.getGroupId());
            dto.setGroupName(group.getGroupName());
            dto.setCreatedBy(group.getCreatedBy());

            // Compte les messages
            if (group.getChat() != null && group.getChat().getMessageList() != null) {
                dto.setMessageCount(group.getChat().getMessageList().size());
            } else {
                dto.setMessageCount(0);
            }

            // Récupère les membres du groupe
            List<GroupMembership> memberships = groupMembershipRepository.findByGroupe(group);
            List<GroupMemberDTO> members = memberships.stream().map(m -> {
                UserDTO user = userClient.getUserById(m.getUserId());
                return new GroupMemberDTO(user.getId(), user.getUserName(), m.getRole().name());
            }).toList();

            dto.setMembers(members);
            result.add(dto);
        }

        return result;
    }
    @Override
    public List<Groupe> getGroupsCreatedBy(String username) {
        return groupeRepository.findByCreatedBy(username);
    }

}
