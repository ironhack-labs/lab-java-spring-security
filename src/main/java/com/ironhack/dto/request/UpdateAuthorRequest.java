package com.ironhack.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UpdateAuthorRequest(
        @NotBlank(message = "Name is required")
        String name
) {}
