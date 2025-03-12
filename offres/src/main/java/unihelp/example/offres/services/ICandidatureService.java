package unihelp.example.offres.services;

import unihelp.example.offres.entities.Candidature;

import java.util.List;

public interface ICandidatureService {
    // Récupérer une candidature par son ID
    Candidature findByid(long id);

    // Récupérer toutes les candidatures
    List<Candidature> findAll();

    // Enregistrer une candidature
    Candidature save(Candidature candidature);

    // Supprimer une candidature
    void delete(Candidature candidature);

    // Ajouter une candidature
    Candidature addCandidature(Candidature candidature);

    // Mettre à jour une candidature
    Candidature updateCandidature(Candidature candidature);
}

