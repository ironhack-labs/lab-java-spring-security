package com.ironhack.dto.response;

import com.ironhack.entity.AuthRole;

import java.util.UUID;

public record AuthResponse(
        UUID id,
        String email,
        AuthRole role
) {}
