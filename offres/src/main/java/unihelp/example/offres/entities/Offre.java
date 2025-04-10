package unihelp.example.offres.entities;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Offre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titre;
    private String description;
    private String localisation;
    @Column(name = "date_publication")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date datePublication =new Date() ;
    private String createdByEmail;
    private String entreprise;

   private LocalDate dateExpiration;
    @Enumerated(EnumType.STRING)
    private Typeoffre typeOffre;

    // Si tu veux la liste des candidatures dans Offre :
    @OneToMany(mappedBy = "offre", cascade = CascadeType.ALL)
    @JsonIgnore // 🛑 pour ne pas inclure la liste dans la réponse JSON
    private List<Candidature> candidatures;



}
