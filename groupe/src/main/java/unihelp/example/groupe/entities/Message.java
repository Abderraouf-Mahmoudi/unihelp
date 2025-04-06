package unihelp.example.groupe.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private Date time = new Date();
    @Column(name = "room_id")
    private String roomId;

    private String senderName;
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "chat_id") // Ceci force Hibernate à ne pas créer chat_chat_id
// ✅ C'est ÇA qu'il faut utiliser ici
    private Chat chat;
}
