package unihelp.example.groupe.services;

import unihelp.example.groupe.dto.GroupMemberDTO;
import unihelp.example.groupe.dto.GroupeWithMembersDTO;
import unihelp.example.groupe.entities.*;

import java.util.List;

public interface IGroupeService {
        public Groupe createGroup(String groupName, List<String> userNames, String createdBy);

        public Groupe addUserToGroup(Long groupId, String userName);

        public Chat sendMessage(Long groupId, String userName, String messageText);

        public List<Message> getMessages(Long groupId);

        List<Groupe> getAllGroups();

        public List<GroupMemberDTO> getGroupMembers(Long groupId);

        Groupe renameGroup(Long groupId, String newName);

        public void leaveGroup(Long groupId, String username);
        List<Groupe> getGroupsForUser(String username);
    void notifyVideoCall(Long groupId, String username);
        void startVideoCall(Long groupId, String username);
        public void handleIncomingWebSocketMessage(Long groupId, Message message);
        public void requestToJoin(Long groupId, String username);
       List<JoinRequest> getPendingRequests(Long groupId);
       public void acceptJoinRequest(Long requestId);
       List<GroupeWithMembersDTO> getAllGroupsWithMembers();
       List<Groupe> getGroupsCreatedBy(String username);
       public void deleteGroup(Long groupId, String username);

}