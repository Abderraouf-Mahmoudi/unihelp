package unihelp.example.offres.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unihelp.example.offres.entities.Offre;
@Repository
public interface IOffreRepository  extends JpaRepository<Offre, Long> {


}
