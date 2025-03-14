package unihelp.example.groupe.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unihelp.example.groupe.entities.User;
import unihelp.example.groupe.services.IUserService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    IUserService userService;

    @GetMapping("/getall")
    public List<User> getAllUser() {
        return userService.findAll();
    }

    // Récupérer une réunion par ID
    @GetMapping("/{iduser}")
    public User getUserById(@PathVariable long iduser) {
        return userService.findById(iduser);
    }

    // Ajouter une nouvelle réunion
    @PostMapping("/create")
    public User createUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    // Mettre à jour une réunion
    @PutMapping("/update")
    public User updateUser(@RequestBody User user ) {
        return userService.updateUser(user);
    }

    // Supprimer une réunion
    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable long id) {
        User user = userService.findById(id);
        if (user != null) {
            userService.delete(user);
        }
    }
    @PutMapping("/{userId}/assign/{reunionId}")
    public ResponseEntity<User> assignUserToReunion(@PathVariable Long userId, @PathVariable Long reunionId) {
        User updatedUser = userService.assignUserToReunion(userId, reunionId);
        return ResponseEntity.ok(updatedUser);
    }

}
