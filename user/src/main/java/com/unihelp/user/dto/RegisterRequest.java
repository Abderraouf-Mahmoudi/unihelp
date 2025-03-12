package com.unihelp.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import com.unihelp.user.entities.UserRole;

@Getter
@Setter
public class RegisterRequest {
    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;

    @Size(max = 1000, message = "Bio cannot exceed 1000 characters")
    private String bio;

    @Size(max = 500, message = "Skills cannot exceed 500 characters")
    private String skills;

    private String profileImage;

    private UserRole role = UserRole.STUDENT;
}
