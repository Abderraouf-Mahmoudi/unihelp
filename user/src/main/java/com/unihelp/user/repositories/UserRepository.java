package com.unihelp.user.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.unihelp.user.entities.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
