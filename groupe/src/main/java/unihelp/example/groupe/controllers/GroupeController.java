package unihelp.example.groupe.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unihelp.example.groupe.entities.Groupe;
import unihelp.example.groupe.services.IGroupeService;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/groupes")
public class GroupeController {
IGroupeService groupeService;
    // Récupérer tous les groupes
    @GetMapping("/getall")
    public List<Groupe> getAllGroupes() {
        return groupeService.findAll();
    }

    // Récupérer un groupe par ID
    @GetMapping("/{id}")
    public Groupe getGroupeById(@PathVariable long id) {
        return groupeService.findById(id);
    }

    @PostMapping("/create")
    public ResponseEntity<Groupe> addGroupe(@RequestBody Groupe groupe) {
        // Ajouter le groupe via le service
        Groupe savedGroupe = groupeService.addGroupe(groupe);

        // Retourner une réponse avec le groupe ajouté
        return ResponseEntity.ok(savedGroupe);
    }
    // Mettre à jour un groupe
    @PutMapping("/update")
    public Groupe updateGroupe(@RequestBody Groupe groupe) {
        return groupeService.updateGroupe(groupe);
    }

    // Supprimer un groupe
    @DeleteMapping("/delete/{id}")
    public void deleteGroupe(@PathVariable long id) {
        Groupe groupe = groupeService.findById(id);
        if (groupe != null) {
            groupeService.delete(groupe);
        }
    }
}
