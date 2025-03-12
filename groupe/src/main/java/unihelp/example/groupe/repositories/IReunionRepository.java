package unihelp.example.groupe.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unihelp.example.groupe.entities.Reunion;

@Repository
public interface IReunionRepository extends JpaRepository<Reunion, Long>
{
}
