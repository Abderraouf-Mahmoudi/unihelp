package unihelp.example.offres.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import unihelp.example.offres.entities.Offre;

import unihelp.example.offres.services.IOffreService;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@AllArgsConstructor
@RequestMapping("/api/offres")
@Slf4j
public class Controlleroffre {

    private final IOffreService offreService;


    @PostMapping
    public ResponseEntity<Offre> createOffre(@RequestBody Offre offre,
                                             @RequestHeader("X-USER-ID") Long userIdAppelant) {
        System.out.println("💬 Offre reçue : " + offre); // ← voir ce qui arrive exactement

        // 💡 Tu peux aussi extraire ça d’un token JWT dans un vrai système sécurisé
        Offre created = offreService.createOffre(offre, userIdAppelant);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }



    @GetMapping
    public List<Offre> getAll() {
        return offreService.getAllOffres();
    }

    @GetMapping("/{id}")
    public Offre getById(@PathVariable Long id) {
        return offreService.getOffreById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOffre(@PathVariable Long id,
                                            @RequestHeader("X-USER-ID") Long userIdAppelant) {
        boolean isDeleted = offreService.deleteOffre(id, userIdAppelant);
        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // Code 204 : Pas de contenu, mais suppression réussie
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Code 404 : Offre non trouvée
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Offre> updateOffre(@PathVariable Long id,
                                             @RequestBody Offre offre,
                                             @RequestHeader("X-USER-ID") Long userIdAppelant) {
        // On vérifie que l'ID correspond à l'ID de l'offre dans le corps de la requête
        if (!id.equals(offre.getId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        // Mise à jour de l'offre
        Offre updatedOffre = offreService.updateOffre(id, offre, userIdAppelant);
        if (updatedOffre != null) {
            return ResponseEntity.ok(updatedOffre);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }



}
