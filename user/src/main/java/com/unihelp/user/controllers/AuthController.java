package com.unihelp.user.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import com.unihelp.user.dto.LoginRequest;
import com.unihelp.user.dto.LoginResponse;
import com.unihelp.user.dto.RegisterRequest;
import com.unihelp.user.entities.User;
import com.unihelp.user.repositories.UserRepository;
import com.unihelp.user.security.JwtUtils;
import com.unihelp.user.services.UserService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        try {
            User user = userService.registerUser(request);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erreur d'inscription : " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        // Log the email being used for login
        System.out.println("Attempting login for email: " + request.getEmail());

        User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> {
                System.out.println("User not found for email: " + request.getEmail());
                return new RuntimeException("User not found");
            });

        if (user.isBanned()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User account is banned.");
        }

        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateToken((UserDetails) authentication.getPrincipal());

        System.out.println("User retrieved: " + user.getEmail());

        return ResponseEntity.ok(LoginResponse.builder()
            .token(jwt)
            .type("Bearer")
            .id(user.getId())
            .email(user.getEmail())
            .role(user.getRole().name())
            .build());
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok("User logged out successfully.");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));
        return ResponseEntity.ok(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/users/{id}/ban")
    public ResponseEntity<String> banUser(@PathVariable Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));
        if (user.isBanned()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already banned.");
        }
        user.setBanned(true);
        userRepository.save(user);
        return ResponseEntity.ok("User banned successfully.");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/users/{id}/unban")
    public ResponseEntity<String> unbanUser(@PathVariable Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));
        if (!user.isBanned()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User is not banned.");
        }
        user.setBanned(false);
        userRepository.save(user);
        return ResponseEntity.ok("User unbanned successfully.");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/admin/users/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));
        user.setFirstName(updatedUser.getFirstName());
        user.setLastName(updatedUser.getLastName());
        user.setEmail(updatedUser.getEmail());
        user.setBio(updatedUser.getBio());
        user.setSkills(updatedUser.getSkills());
        user.setProfileImage(updatedUser.getProfileImage());
        user.setRole(updatedUser.getRole());
        userRepository.save(user);
        return ResponseEntity.ok("User details updated successfully.");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/admin/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.delete(user);
        return ResponseEntity.ok("User deleted successfully.");
    }
}
