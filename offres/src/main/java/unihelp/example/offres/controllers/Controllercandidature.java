package unihelp.example.offres.controllers;


import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import unihelp.example.offres.entities.Candidature;

import unihelp.example.offres.services.ICandidatureService;



import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;


@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/candidatures")
public class Controllercandidature {

    private final ICandidatureService candidatureService;



    @GetMapping("/download")
    public void downloadCv(@RequestParam String path, HttpServletResponse response) throws IOException {
        File file = new File(path); // 🔥 On utilise directement le chemin reçu

        if (file.exists()) {
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");
            Files.copy(file.toPath(), response.getOutputStream());
            response.getOutputStream().flush();
        } else {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.getWriter().write("❌ Fichier non trouvé : " + file.getAbsolutePath());
        }
    }


    @PostMapping("/offre/upload/{offreId}")
    public ResponseEntity<String> postulerAvecFichier(
            @PathVariable Long offreId,
            @RequestParam("message") String message,
            @RequestParam("cvFile") MultipartFile cvFile,
            @RequestHeader("X-USER-ID") Long userIdAppelant) {
        try {
            // ✅ 1. Obtenir un chemin ABSOLU correct (ex: C:/Users/.../mon-projet/uploads)
            String uploadPath = System.getProperty("user.dir") + File.separator + "uploads";
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs(); // créer dossier s'il n'existe pas
            }

            // ✅ 2. Enregistrer le fichier avec un nom unique
            String filename = System.currentTimeMillis() + "-" + cvFile.getOriginalFilename();
            String filepath = uploadPath + File.separator + filename;

            cvFile.transferTo(new File(filepath)); // ✅ évite les erreurs

            // ✅ 3. Création de l'objet Candidature
            Candidature candidature = new Candidature();
            candidature.setMessage(message);
            candidature.setCvUrl(filepath); // chemin complet ou à adapter selon ton affichage
            candidatureService.postuler(offreId, candidature, userIdAppelant);

            return ResponseEntity.ok("✅ Candidature enregistrée avec CV");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("❌ Erreur d'enregistrement: " + e.getMessage());
        }
    }


    @GetMapping("/offre/{offreId}")
    public List<Candidature> getByOffre(@PathVariable Long offreId) {
        return candidatureService.getByOffreId(offreId);
    }

    @GetMapping("/admin/candidatures")
    public List<Candidature> getCandidaturesByAdmin(@RequestParam("email") String email) {
        log.info("📩 Email reçu pour recherche : " + email); // Ajoute ce log
        return candidatureService.getCandidaturesByAdminEmail(email);
    }

}


