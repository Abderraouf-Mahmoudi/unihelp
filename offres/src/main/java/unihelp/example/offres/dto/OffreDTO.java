package unihelp.example.offres.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class OffreDTO {
    private Long offreId;
    private String titre;
    private String description;
    private String typeOffre;
    private Date dateCreation;
    private String entreprise;
}
