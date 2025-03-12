package unihelp.example.offres.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import unihelp.example.offres.entities.Candidature;
import unihelp.example.offres.repositories.ICandidatureRepository;

import java.util.List;
import java.util.Optional;
@Service
@AllArgsConstructor
public class ICandidatureServiceImpl implements ICandidatureService {
    ICandidatureRepository candidatureRepo;

    @Override
    public Candidature findByid(long id) {
        Optional<Candidature> candidature = candidatureRepo.findById(id);
        return candidature.orElse(null); // Retourne null si non trouvé
    }

    @Override
    public List<Candidature> findAll() {
        return candidatureRepo.findAll();
    }

    @Override
    public Candidature save(Candidature candidature) {
        return candidatureRepo.save(candidature);
    }

    @Override
    public void delete(Candidature candidature) {
        candidatureRepo.delete(candidature);
    }

    @Override
    public Candidature addCandidature(Candidature candidature) {
        return candidatureRepo.save(candidature);
    }

    @Override
    public Candidature updateCandidature(Candidature candidature) {
            return candidatureRepo.save(candidature);

    }
}








