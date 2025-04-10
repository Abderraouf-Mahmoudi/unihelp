package unihelp.example.offres.services;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import unihelp.example.offres.Client.UserClient;

import unihelp.example.offres.dto.UserDTO;
import unihelp.example.offres.entities.Offre;

import unihelp.example.offres.repositories.IOffreRepository;



import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class IOffreServiceImpl implements IOffreService {

    private final IOffreRepository offreRepository;
  private final UserClient userClient;

    @Override
    public Offre createOffre(Offre offre, Long userIdAppelant) {
        UserDTO user = userClient.getUserById(userIdAppelant); // Feign récupère l'utilisateur

        if (!"ADMIN".equalsIgnoreCase(user.getUserRole())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Seuls les admins peuvent créer des offres");
        }

        offre.setCreatedByEmail(user.getEmail()); // 👉 on associe l'email ici
        log.info("Offre créée par : " + user.getEmail());

        return offreRepository.save(offre);
    }


    @Override
    public List<Offre> getAllOffres() {
        return offreRepository.findAll();
    }

    @Override
    public Offre getOffreById(Long id) {
        return offreRepository.findById(id).orElse(null);
    }


    @Override
    public Offre updateOffre(Long id, Offre offre, Long userIdAppelant) {
        // Récupérer l'offre existante
        Offre existingOffre = offreRepository.findById(id).orElse(null);
        if (existingOffre == null) {
            return null; // L'offre n'existe pas
        }

        // Vérification du rôle et de l'auteur de l'offre
        UserDTO user = userClient.getUserById(userIdAppelant);
        if (!"ADMIN".equalsIgnoreCase(user.getUserRole()) && !existingOffre.getCreatedByEmail().equals(user.getEmail())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Vous n'êtes pas autorisé à mettre à jour cette offre");
        }

        // Mise à jour des champs de l'offre
        existingOffre.setTitre(offre.getTitre());
        existingOffre.setDescription(offre.getDescription());
        existingOffre.setTypeOffre(offre.getTypeOffre());

        // Sauvegarder l'offre mise à jour
        return offreRepository.save(existingOffre);
    }


    @Override
    public boolean deleteOffre(Long id, Long userIdAppelant) {
        // Récupérer l'offre existante
        Offre existingOffre = offreRepository.findById(id).orElse(null);
        if (existingOffre == null) {
            return false; // L'offre n'existe pas
        }

        // Vérification du rôle et de l'auteur de l'offre
        UserDTO user = userClient.getUserById(userIdAppelant);
        if (!"ADMIN".equalsIgnoreCase(user.getUserRole()) && !existingOffre.getCreatedByEmail().equals(user.getEmail())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Vous n'êtes pas autorisé à supprimer cette offre");
        }

        // Suppression de l'offre
        offreRepository.delete(existingOffre);
        return true;
    }







}