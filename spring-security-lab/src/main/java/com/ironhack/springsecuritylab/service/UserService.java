package com.ironhack.springsecuritylab.service;

import com.ironhack.springsecuritylab.entity.User;
import com.ironhack.springsecuritylab.entity.UserRole;
import com.ironhack.springsecuritylab.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User register(String email, String rawPassword, String fullName) {
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("A user with email " + email + " already exists");
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setRole(UserRole.USER);

        return userRepository.save(user);
    }

    @Transactional
    public User create(String email, String rawPassword, UserRole role) {
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("A user with email " + email + " already exists");
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setRole(role != null ? role : UserRole.USER);

        return userRepository.save(user);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }

    public boolean checkPassword(User user, String rawPassword) {
        // matches() compares a raw password against the stored BCrypt hash
        return passwordEncoder.matches(rawPassword, user.getPassword());
    }
}