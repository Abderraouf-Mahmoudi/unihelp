package unihelp.example.groupe.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import unihelp.example.groupe.entities.Chat;

public interface IChatRepository extends JpaRepository<Chat, Long> {
}
