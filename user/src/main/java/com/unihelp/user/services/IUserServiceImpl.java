package com.unihelp.user.services;

import com.unihelp.user.entities.User;
import com.unihelp.user.repositories.IUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class IUserServiceImpl implements IUserService {

    private final IUserRepository userRepository;

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }


    @Override
    public User loginUser(String email, String password) {
        // Chercher l'utilisateur par email
        Optional<User> optionalUser = userRepository.findByEmail(email);

        // Si aucun utilisateur trouvé, retourner null
        if (optionalUser.isEmpty()) {
            return null; // Aucun utilisateur trouvé
        }

        User user = optionalUser.get(); // Obtenir l'utilisateur trouvé

        // Vérifier si le mot de passe correspond
        if (user.getPassword() != null && user.getPassword().equals(password)) {
            return user; // Authentification réussie ✅
        }

        return null; // Mot de passe incorrect
    }


    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé : " + email));
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
    }

    @Override
    public User updateUser(Long id, User updatedUser) {
        // Recherche l'utilisateur à mettre à jour
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        // Mettre à jour les champs nécessaires
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPassword(updatedUser.getPassword());
        existingUser.setUserRole(updatedUser.getUserRole()); // Exemple de mise à jour du rôle

        return userRepository.save(existingUser); // Enregistrer les modifications
    }

    // Méthode facultative pour trouver un utilisateur par email (en cas de besoin)
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
    }
}
