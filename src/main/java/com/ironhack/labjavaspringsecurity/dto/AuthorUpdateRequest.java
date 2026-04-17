package com.ironhack.labjavaspringsecurity.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthorUpdateRequest(
        @NotBlank String name
) {
}

