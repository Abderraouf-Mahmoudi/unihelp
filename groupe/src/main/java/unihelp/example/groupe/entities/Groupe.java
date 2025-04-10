package unihelp.example.groupe.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class Groupe
{
    @Id
    @GeneratedValue()
    private Long groupId;
    private String groupName;
    @OneToMany(mappedBy = "groupe", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<GroupMembership> members = new HashSet<>();
    private String createdBy;
    private Long createdById;     // ✅ l’ID à ajouter si pas encore fait

    @OneToMany(mappedBy = "groupe", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private Set<JoinRequest> joinRequests = new HashSet<>();
    @Transient
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer messageCount;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "chat_id", referencedColumnName = "chatId")
    private Chat chat;

}
