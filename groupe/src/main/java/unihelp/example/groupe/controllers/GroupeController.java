package unihelp.example.groupe.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unihelp.example.groupe.dto.CreateGroupRequest;
import unihelp.example.groupe.dto.GroupMemberDTO;
import unihelp.example.groupe.dto.GroupeWithMembersDTO;
import unihelp.example.groupe.entities.Chat;
import unihelp.example.groupe.entities.Groupe;
import unihelp.example.groupe.entities.JoinRequest;
import unihelp.example.groupe.entities.Message;
import unihelp.example.groupe.services.IGroupeService;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/groupes")
public class GroupeController {
IGroupeService groupeService ;

    // Récupérer tous les groupes
    @PostMapping("/create")
    public ResponseEntity<Groupe> createGroup(@RequestBody CreateGroupRequest request) {
        System.out.println("➡️ JSON Reçu:");
        System.out.println("GroupName = " + request.getGroupName());
        System.out.println("UserNames = " + request.getUserNames());
        return ResponseEntity.ok(groupeService.createGroup(request.getGroupName(), request.getUserNames(),request.getCreatedBy()));
    }
    @GetMapping("/{groupId}/members")
    public ResponseEntity<List<GroupMemberDTO>> getGroupMembers(@PathVariable Long groupId) {
        return ResponseEntity.ok(groupeService.getGroupMembers(groupId));
    }
    @PutMapping("/{groupId}/rename")
    public ResponseEntity<Groupe> renameGroup(
            @PathVariable Long groupId,
            @RequestBody String newName) {
        newName = newName.replace("\"", ""); // Nettoie les guillemets JSON
        Groupe updated = groupeService.renameGroup(groupId, newName);
        return ResponseEntity.ok(updated);
    }
    @PostMapping("/{groupId}/addUser")
    public ResponseEntity<Groupe> addUserToGroup(@PathVariable Long groupId, @RequestParam String userName) {
        return ResponseEntity.ok(groupeService.addUserToGroup(groupId, userName));
    }
    @PostMapping("/{groupId}/sendMessage")
    public ResponseEntity<Chat> sendMessage(
            @PathVariable Long groupId,
            @RequestParam String userName,
            @RequestParam String message) {
        System.out.println("✅ sendMessage called with groupId = " + groupId + ", userName = " + userName + ", message = " + message);

        return ResponseEntity.ok(groupeService.sendMessage(groupId, userName, message));
    }
    @GetMapping("/{groupId}/messages")
    public ResponseEntity<List<Message>> getMessages(@PathVariable Long groupId) {
        return ResponseEntity.ok(groupeService.getMessages(groupId));
    }

    @DeleteMapping("/{groupId}/leave")
    public ResponseEntity<Void> leaveGroup(@PathVariable Long groupId, @RequestParam String username) {
        groupeService.leaveGroup(groupId, username);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/byUser")
    public ResponseEntity<List<Groupe>> getGroupsForUser(@RequestParam String username) {
        return ResponseEntity.ok(groupeService.getGroupsForUser(username));
    }
    @GetMapping("/all")
    public ResponseEntity<List<Groupe>> getAllGroups() {
        return ResponseEntity.ok(groupeService.getAllGroups());
    }


    @PostMapping("/{groupId}/video-call-start")
    public ResponseEntity<Void> startVideoCall(@PathVariable Long groupId, @RequestParam String username) {
        groupeService.startVideoCall(groupId, username);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/{groupId}/join-request")
    public ResponseEntity<Void> requestToJoin(@PathVariable Long groupId, @RequestParam String username) {
        groupeService.requestToJoin(groupId, username);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/{groupId}/pending-requests")
    public ResponseEntity<List<JoinRequest>> getPendingRequests(@PathVariable Long groupId) {
        return ResponseEntity.ok(groupeService.getPendingRequests(groupId));
    }
    @PostMapping("/join-request/{requestId}/accept")
    public ResponseEntity<Void> acceptJoinRequest(@PathVariable Long requestId) {
        groupeService.acceptJoinRequest(requestId);
        return ResponseEntity.ok().build();
    }
//hatheya kif ya3mel verification est ce memebre ou admine de group afficher le bouton open chat l'autre afficher le boton rejoindre le group
    @GetMapping("/with-members")
    public ResponseEntity<List<GroupeWithMembersDTO>> getGroupsWithMembers() {
        return ResponseEntity.ok(groupeService.getAllGroupsWithMembers());
    }
    @GetMapping("/created-by")
    public ResponseEntity<List<Groupe>> getGroupsCreatedBy(@RequestParam String username) {
        return ResponseEntity.ok(groupeService.getGroupsCreatedBy(username));
    }
    @DeleteMapping("/group/{groupId}")
    public ResponseEntity<?> deleteGroup(@PathVariable Long groupId,
                                         @RequestParam String username) {
        try {
            groupeService.deleteGroup(groupId, username);
            return ResponseEntity.ok().body("Groupe supprimé avec succès.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }



}

