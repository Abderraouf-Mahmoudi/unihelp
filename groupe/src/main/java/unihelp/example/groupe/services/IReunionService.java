package unihelp.example.groupe.services;

import unihelp.example.groupe.entities.Reunion;

import java.util.List;

public interface IReunionService  {
    // Récupérer une réunion par son ID
    Reunion findById(long id);

    // Récupérer toutes les réunions
    List<Reunion> findAll();

    // Enregistrer une réunion
    Reunion save(Reunion reunion);

    // Supprimer une réunion
    void delete(Reunion reunion);

    // Ajouter une réunion
    Reunion addReunion(Reunion reunion);

    // Mettre à jour une réunion
    Reunion updateReunion(Reunion reunion);
    Reunion addReunionToGroupe(Long groupeId, Long reunionId);

}
