package unihelp.example.groupe.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import unihelp.example.groupe.entities.GroupMembership;
import unihelp.example.groupe.entities.Groupe;

import java.util.List;
import java.util.Optional;

public interface IGroupMembershipRepository extends JpaRepository<GroupMembership, Long> {
    boolean existsByUserIdAndGroupe(Long userId, Groupe groupe);
    // ✅ C'est cette ligne qui est nécessaire pour que le code compile
    List<GroupMembership> findByGroupe(Groupe groupe);
    Optional<GroupMembership> findByUserIdAndGroupe(Long userId, Groupe group);
    List<GroupMembership> findByUserId(Long userId);

}

