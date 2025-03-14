package unihelp.example.groupe.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import unihelp.example.groupe.entities.User;

public interface IUserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

}
