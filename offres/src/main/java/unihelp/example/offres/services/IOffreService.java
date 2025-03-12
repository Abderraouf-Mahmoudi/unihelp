package unihelp.example.offres.services;

import unihelp.example.offres.entities.Offre;

import java.util.List;

public interface IOffreService {
    // Récupérer un foyer par son ID
    Offre findByid(long id);

    // Récupérer tous les foyers
    List<Offre> findAll();


   Offre save(Offre offre);

    // Supprimer un offre
    void delete(Offre offre);



    // Ajouter un foyer
    Offre addOffre(Offre offre);

    // Mettre à jour un foyer
    Offre updateOffre(Offre o);
}
