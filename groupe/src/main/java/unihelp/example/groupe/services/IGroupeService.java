package unihelp.example.groupe.services;

import unihelp.example.groupe.entities.Groupe;

import java.util.List;

public interface IGroupeService  {

        // Récupérer un groupe par son ID
        Groupe findById(long id);

        // Récupérer tous les groupes
        List<Groupe> findAll();

        // Enregistrer un groupe
        Groupe save(Groupe groupe);

        // Supprimer un groupe
        void delete(Groupe groupe);

        // Ajouter un groupe
        Groupe addGroupe(Groupe groupe);

        // Mettre à jour un groupe
        Groupe updateGroupe(Groupe groupe);
    }


