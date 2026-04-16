package com.ironhack.mapper;

import com.ironhack.dto.request.RegisterRequest;
import com.ironhack.dto.response.AuthResponse;
import com.ironhack.entity.Auth;
import org.springframework.stereotype.Component;

@Component
public class AuthMapper {
    public Auth toEntity(RegisterRequest request) {
        return new Auth(
                request.email(),
                request.password()
        );
    }

    public AuthResponse toResponse(Auth auth) {
        return new AuthResponse(
                auth.getId(),
                auth.getEmail(),
                auth.getRole());
    }
}
