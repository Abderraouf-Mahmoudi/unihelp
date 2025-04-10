package unihelp.example.offres.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unihelp.example.offres.entities.Candidature;
import unihelp.example.offres.entities.Offre;

import java.util.List;

@Repository
public interface ICandidatureRepository extends JpaRepository<Candidature, Long> {
    List<Candidature> findByOffreId(Long offreId);

}
