package unihelp.example.groupe.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import unihelp.example.groupe.entities.Reunion;
import unihelp.example.groupe.entities.User;
import unihelp.example.groupe.repositories.IReunionRepository;
import unihelp.example.groupe.repositories.IUserRepository;

import java.util.List;
@Service
@AllArgsConstructor
public class IUserServiceImpl implements IUserService {

    IUserRepository userRepository;
    IReunionRepository reunionRepository;

    @Override
    public User findById(long iduser) {
        return userRepository.findById(iduser)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable avec l'ID : " + iduser));
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Override
    public User addUser(User user) {


        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user) {
        if (!userRepository.existsById(user.getIduser())) {
            throw new RuntimeException("Impossible de mettre à jour : utilisateur introuvable.");
        }
        return userRepository.save(user);
    }
    public User assignUserToReunion(Long iduser, Long reunionid) {
        User user = userRepository.findById(iduser)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Reunion reunion = reunionRepository.findById(reunionid)
                .orElseThrow(() -> new RuntimeException("Reunion not found"));

        user.setReunion(reunion);
        return userRepository.save(user);
    }
}
