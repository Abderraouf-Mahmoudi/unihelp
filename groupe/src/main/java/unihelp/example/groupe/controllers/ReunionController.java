package unihelp.example.groupe.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import unihelp.example.groupe.entities.Reunion;
import unihelp.example.groupe.services.IReunionService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/reunions")
public class ReunionController {
    IReunionService reunionService;
    // Récupérer toutes les réunions
    @GetMapping("/getall")
    public List<Reunion> getAllReunions() {
        return reunionService.findAll();
    }

    // Récupérer une réunion par ID
    @GetMapping("/{id}")
    public Reunion getReunionById(@PathVariable long id) {
        return reunionService.findById(id);
    }

    // Ajouter une nouvelle réunion
    @PostMapping("/create")
    public Reunion createReunion(@RequestBody Reunion reunion) {
        return reunionService.addReunion(reunion);
    }

    // Mettre à jour une réunion
    @PutMapping("/update")
    public Reunion updateReunion(@RequestBody Reunion reunion) {
        return reunionService.updateReunion(reunion);
    }

    // Supprimer une réunion
    @DeleteMapping("/delete/{id}")
    public void deleteReunion(@PathVariable long id) {
        Reunion reunion = reunionService.findById(id);
        if (reunion != null) {
            reunionService.delete(reunion);
        }
    }
}
