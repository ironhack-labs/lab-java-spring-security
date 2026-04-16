package com.ironhack.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CreateAuthorRequest(
        @NotBlank(message = "Name is required")
        String name
) {}
