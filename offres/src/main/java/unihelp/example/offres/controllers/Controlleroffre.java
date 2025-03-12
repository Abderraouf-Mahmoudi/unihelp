package unihelp.example.offres.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import unihelp.example.offres.entities.Offre;
import unihelp.example.offres.services.IOffreService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/offres")
public class Controlleroffre {
    private final IOffreService offreService;

    // Récupérer toutes les offres
    @GetMapping("/getall")
    public List<Offre> getAllOffres() {
        return offreService.findAll();
    }

    // Créer une offre
    @PostMapping("/createoffre")
    public Offre createOffre(@RequestBody Offre offre) {
        return offreService.addOffre(offre);
    }

    // Mettre à jour une offre
    @PutMapping("/update")
    public Offre updateOffre(@RequestBody Offre offre) {
        return offreService.updateOffre(offre);
    }

    // Récupérer une offre par son ID
    @GetMapping("/{id}")
    public Offre getOffreById(@PathVariable long id) {
        return offreService.findByid(id);
    }

    // Supprimer une offre
    @DeleteMapping("/delete")
    public void deleteOffre(@RequestBody Offre offre) {
        offreService.delete(offre);
    }
}