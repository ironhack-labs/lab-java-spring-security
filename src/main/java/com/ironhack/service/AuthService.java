package com.ironhack.service;

import com.ironhack.entity.AuthRole;
import com.ironhack.mapper.AuthMapper;
import com.ironhack.dto.request.RegisterRequest;
import com.ironhack.dto.response.AuthResponse;
import com.ironhack.entity.Auth;
import com.ironhack.exception.ConflictException;
import com.ironhack.repository.AuthRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService implements UserDetailsService {
    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthMapper authMapper;

    public AuthService(AuthRepository authRepository, PasswordEncoder passwordEncoder, AuthMapper authMapper) {
        this.authRepository = authRepository;
        this.passwordEncoder = passwordEncoder;
        this.authMapper = authMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Auth auth = authRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("Auth not found: " + email));

        return User.builder()
            .username(auth.getEmail())
            .password(auth.getPassword())
            .roles(auth.getRole().name())
            .build();
    }

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (authRepository.existsByEmail(request.email())) {
            throw new ConflictException("Email already exists");
        }

        Auth auth = authMapper.toEntity(request);
        auth.setRole(AuthRole.USER);
        auth.setPassword(passwordEncoder.encode(request.password()));

        Auth savedAuth = authRepository.save(auth);
        return authMapper.toResponse(savedAuth);
    }
}
