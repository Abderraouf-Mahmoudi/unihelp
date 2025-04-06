package unihelp.example.groupe.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import unihelp.example.groupe.entities.Groupe;
import unihelp.example.groupe.entities.JoinRequest;

import java.util.List;

public interface JoinRequestRepository extends JpaRepository<JoinRequest, Long> {
    List<JoinRequest> findByGroupeGroupIdAndAcceptedFalse(Long groupId); // ✅ ✅ ✅
    boolean existsByUsernameAndGroupe(String username, Groupe groupe);
    void deleteByUsernameAndGroupe(String username, Groupe groupe);
    List<JoinRequest> findByUsernameAndGroupe(String username, Groupe groupe);

}
