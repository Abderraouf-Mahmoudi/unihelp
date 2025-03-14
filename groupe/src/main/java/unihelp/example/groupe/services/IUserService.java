package unihelp.example.groupe.services;

import unihelp.example.groupe.entities.Reunion;
import unihelp.example.groupe.entities.User;
import java.util.List;

public interface IUserService {
    // Récupérer un utilisateur par son ID
    User findById(long iduser);

    // Récupérer tous les utilisateurs
    List<User> findAll();

    User save(User user);
    User addUser(User user);

    // Supprimer un utilisateur
    void delete(User user);

    // Mettre à jour un utilisateur
    User updateUser(User user);

    // Ajouter un utilisateur à une réunion
    public User assignUserToReunion(Long iduser, Long reunionid) ;
}
