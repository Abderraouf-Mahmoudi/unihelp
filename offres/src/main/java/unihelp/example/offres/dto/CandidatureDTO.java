package unihelp.example.offres.dto;

import lombok.Getter;
import lombok.Setter;
import unihelp.example.offres.entities.Offre;

import java.util.Date;
import java.util.List;
@Getter
@Setter
public class CandidatureDTO {

        private Long candidatureId;
        private Date dateCandidature;
       // private String statut;
        private String commentaire;
        private String cv;
        private List<OffreDTO> offres;



}


