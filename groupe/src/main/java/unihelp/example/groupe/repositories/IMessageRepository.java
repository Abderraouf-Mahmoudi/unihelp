package unihelp.example.groupe.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import unihelp.example.groupe.entities.Message;

public interface IMessageRepository  extends JpaRepository<Message, Long> {
}

