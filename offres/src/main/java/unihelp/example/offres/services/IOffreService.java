package unihelp.example.offres.services;


import unihelp.example.offres.dto.UserDTO;
import unihelp.example.offres.entities.Offre;
import unihelp.example.offres.entities.Typeoffre;

import java.util.List;


public interface IOffreService {
  public List<Offre> getAllOffres();
  public Offre getOffreById(Long id);
  public Offre createOffre(Offre offre, Long userIdAppelant);
  Offre updateOffre(Long id, Offre offre, Long userIdAppelant);
  boolean deleteOffre(Long id, Long userIdAppelant);

}
