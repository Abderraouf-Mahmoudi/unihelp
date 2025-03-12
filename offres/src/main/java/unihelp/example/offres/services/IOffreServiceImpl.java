package unihelp.example.offres.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import unihelp.example.offres.entities.Offre;
import unihelp.example.offres.repositories.IOffreRepository;
import com.unihelp.user.entities.UserRole;

import java.util.List;

@Service
@AllArgsConstructor
public class IOffreServiceImpl implements IOffreService {
    private final IOffreRepository offreRepo;

    @Override
    public Offre findByid(long id) {
        return offreRepo.findById(id).orElse(null);
    }

    @Override
    public List<Offre> findAll() {
        return offreRepo.findAll();
    }

    @Override
    public Offre save(Offre offre) {
        return offreRepo.save(offre);
    }

    @Override
    public void delete(Offre offre) {
        offreRepo.delete(offre);
    }

    @Override
    public Offre addOffre(Offre offre) {
        // Vérification que le rôle utilisateur est valide
        if (offre.getUserRole() == null) {
            throw new IllegalArgumentException("Le rôle utilisateur est obligatoire !");
        }

        return offreRepo.save(offre);
    }

    @Override
    public Offre updateOffre(Offre o) {
        return offreRepo.save(o);
    }



}
