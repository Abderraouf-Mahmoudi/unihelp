package unihelp.example.groupe.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import unihelp.example.groupe.client.UserClient;
import unihelp.example.groupe.entities.Groupe;
import unihelp.example.groupe.repositories.IGroupeRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class IGroupeServiceImpl implements IGroupeService {
    IGroupeRepository groupeRepository;
     UserClient userClient;
    @Override
    public Groupe findById(long id) {
        return groupeRepository.findById(id).get();
    }

    @Override
    public List<Groupe> findAll() {
        return groupeRepository.findAll();
    }

    @Override
    public Groupe save(Groupe groupe) {
        return groupeRepository.save(groupe);
    }

    @Override
    public void delete(Groupe groupe) {
   groupeRepository.delete(groupe);
    }
    @Override
// Méthode pour ajouter un groupe
    public Groupe addGroupe(Groupe groupe) {
        // Enregistrer le groupe directement sans vérification de rôle
        return groupeRepository.save(groupe);
    }



    @Override
    public Groupe updateGroupe(Groupe groupe) {
        return groupeRepository.save(groupe);
    }
}
