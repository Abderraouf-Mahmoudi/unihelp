package unihelp.example.groupe.services;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import unihelp.example.groupe.entities.Groupe;
import unihelp.example.groupe.entities.Reunion;
import unihelp.example.groupe.repositories.IGroupeRepository;
import unihelp.example.groupe.repositories.IReunionRepository;
import unihelp.example.groupe.repositories.IUserRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class IReunionServiceImpl implements IReunionService {
    IReunionRepository repo;
    IUserRepository userRepo;
    IGroupeRepository groupeRepo;
    @Override
    public Reunion findById(long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public List<Reunion> findAll() {
        return repo.findAll();
    }

    @Override
    public Reunion save(Reunion reunion) {
        return repo.save(reunion);
    }

    @Override
    public void delete(Reunion reunion) {
        repo.delete(reunion);
    }

    @Override
    public Reunion addReunion(Reunion reunion) {
        return repo.save(reunion);
    }

    @Override
    public Reunion updateReunion(Reunion reunion) {
        return repo.save(reunion);
    }

    @Override
    public Reunion addReunionToGroupe(Long groupeId, Long reunionId) {
        Groupe groupe = groupeRepo.findById(groupeId)
                .orElseThrow(() -> new RuntimeException("Groupe non trouvé"));

        // Recherche de la réunion par son ID
        Reunion reunion = repo.findById(reunionId)
                .orElseThrow(() -> new RuntimeException("Réunion non trouvée"));

        // Ajouter ce groupe à la réunion
        reunion.getGroupes().add(groupe);

        // Lier la réunion au groupe (mettre à jour la relation ManyToOne dans Groupe)
        groupe.setReunion(reunion);

        // Sauvegarder les changements dans la base de données
        repo.save(reunion);
        groupeRepo.save(groupe);

        return reunion;
    }



}

