package com.ironhack.repository;

import com.ironhack.entity.Auth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AuthRepository extends JpaRepository<Auth, UUID> {
    Optional<Auth> findByEmail(String email);
    boolean existsByEmail(String email);
}
