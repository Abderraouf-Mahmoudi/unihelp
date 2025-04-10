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


    /*@Override
    public User updateUser(String userName, User updatedUser) {
        User existingUser = userRepository.findByUserName(userName)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        // Mettre à jour les champs
        existingUser.setUserName(updatedUser.getUserName());


        return userRepository.save(existingUser);
    }
    */

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserByUserName(String username) {
        List<User> users = userRepository.findByUserName(username);
        if (users.isEmpty()) {
            throw new RuntimeException("Utilisateur non trouvé : " + username);
        }
        return users.get(0); // même logique ici : on prend le premier
    }



    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
    }
}



