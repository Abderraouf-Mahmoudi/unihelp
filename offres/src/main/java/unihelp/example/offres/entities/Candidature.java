package unihelp.example.offres.entities;



import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
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
    private Long id;

    private Long userId;
    private String message;
    private String cvUrl;
    private LocalDate dateDepot = LocalDate.now();
    @ManyToOne
    @JoinColumn(name = "offre_id") // clé étrangère
    private Offre offre;
}
