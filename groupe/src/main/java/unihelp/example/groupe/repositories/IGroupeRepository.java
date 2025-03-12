package unihelp.example.groupe.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unihelp.example.groupe.entities.Groupe;

@Repository
public interface IGroupeRepository extends JpaRepository<Groupe, Long> {
}
