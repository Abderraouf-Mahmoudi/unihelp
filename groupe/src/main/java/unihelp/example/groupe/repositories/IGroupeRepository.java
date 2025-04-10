package unihelp.example.groupe.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unihelp.example.groupe.entities.GroupMembership;
import unihelp.example.groupe.entities.Groupe;

import java.util.List;
import java.util.Optional;

@Repository
public interface IGroupeRepository extends JpaRepository<Groupe, Long> {
    List<Groupe> findByCreatedBy(String username);

}
