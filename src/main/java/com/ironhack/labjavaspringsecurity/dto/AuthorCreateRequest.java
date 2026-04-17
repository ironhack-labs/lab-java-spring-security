package com.ironhack.labjavaspringsecurity.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthorCreateRequest(
        @NotBlank String name
) {
}

