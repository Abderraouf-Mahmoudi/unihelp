package unihelp.example.offres.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Candidature {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private Date dateCandidature;
    private String statut;
    private String commentaire;
    private String cv;
    @OneToMany(mappedBy = "candidature")
    private List<Offre> offres;


}
