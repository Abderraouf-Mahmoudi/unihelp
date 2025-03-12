package unihelp.example.offres.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import unihelp.example.offres.entities.Candidature;
import unihelp.example.offres.services.ICandidatureService;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/candidatures")
public class Controllercandidature {
ICandidatureService candidatureService;

    @GetMapping("/getall")
    public List<Candidature> getAllCandidatures() {
        return candidatureService.findAll();
    }

    // Récupérer une candidature par ID
    @GetMapping("/{id}")
    public Candidature getCandidatureById(@PathVariable long id) {
        return candidatureService.findByid(id);
    }

    // Ajouter une nouvelle candidature
    @PostMapping("/create")
    public Candidature createCandidature(@RequestBody Candidature candidature) {
        return candidatureService.addCandidature(candidature);
    }

    // Mettre à jour une candidature
    @PutMapping("/update")
    public Candidature updateCandidature(@RequestBody Candidature candidature) {
        return candidatureService.updateCandidature(candidature);
    }

    // Supprimer une candidature
    @DeleteMapping("/delete/{id}")
    public void deleteCandidature(@PathVariable long id) {
        Candidature candidature = candidatureService.findByid(id);
        if (candidature != null) {
            candidatureService.delete(candidature);
        }
    }
}

