package unihelp.example.groupe.entities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long iduser;
    private String name;
    private String email;

    @Enumerated(EnumType.STRING)
    private Typerole role;

    @ManyToOne
    private Reunion reunion;
  /*  @JoinColumn(name = "reunion_id") // Clé étrangère pour mieux structurer la relation
    private Reunion reunion;*/
}
