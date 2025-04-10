package com.unihelp.user.services;

import com.unihelp.user.entities.User;

import java.util.List;

    public interface IUserService {
        // Récupérer un utilisateur par son ID
        public User createUser(User user);

        public User updateUser(Long id, User updatedUser);        // Récupérer tous les utilisateurs
        public List<User> getAllUsers();
        public User getUserByEmail(String email);
        public User getUserById(Long id);
        public User loginUser(String email, String password);

    }

