package com.unihelp.user.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    public ResponseEntity<User> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(userService.registerUser(request));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        // Log the email being used for login
        System.out.println("Attempting login for email: " + request.getEmail());

        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateToken((UserDetails) authentication.getPrincipal());
        
        // Log before retrieving the user
        System.out.println("Retrieving user with email: " + request.getEmail());

        User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> {
                System.out.println("User not found for email: " + request.getEmail());
                return new RuntimeException("User not found");
            });

        System.out.println("User retrieved: " + user.getEmail());

        return ResponseEntity.ok(LoginResponse.builder()
            .token(jwt)
            .type("Bearer")
            .id(user.getId())
            .email(user.getEmail())
            .role(user.getRole().name())
            .build());
    }


}
