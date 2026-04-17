package com.ironhack.springsecuritylab.controller;

import com.ironhack.springsecuritylab.dto.request.LoginRequest;
import com.ironhack.springsecuritylab.dto.request.RegisterRequest;
import com.ironhack.springsecuritylab.dto.response.AuthResponse;
import com.ironhack.springsecuritylab.entity.UserRole;
import com.ironhack.springsecuritylab.security.JWTTokenProvider;
import com.ironhack.springsecuritylab.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import com.ironhack.springsecuritylab.entity.User;
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JWTTokenProvider jwtTokenProvider;

    public AuthController(UserService userService,
                          AuthenticationManager authenticationManager,
                          JWTTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        UserRole role = UserRole.USER;
        if (request.getRole() != null) {
            role = UserRole.valueOf(request.getRole().toUpperCase());
        }

        User user = userService.create(
                request.getEmail(),
                request.getPassword(),
                role
        );

        String token = jwtTokenProvider.generateToken(
                user.getEmail(),
                user.getRole().name()
        );

        AuthResponse response = new AuthResponse();
        response.setToken(token);
        response.setEmail(user.getEmail());
        response.setRole(user.getRole().name());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        // AuthenticationManager verifies credentials via CustomUserDetailsService + BCrypt
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(), request.getPassword())
        );

        User user = userService.findByEmail(request.getEmail());
        String token = jwtTokenProvider.generateToken(
                user.getEmail(),
                user.getRole().name()
        );

        AuthResponse response = new AuthResponse();
        response.setToken(token);
        response.setEmail(user.getEmail());
        response.setRole(user.getRole().name());

        return ResponseEntity.ok(response);
    }
}

