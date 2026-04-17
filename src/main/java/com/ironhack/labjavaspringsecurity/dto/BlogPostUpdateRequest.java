package com.ironhack.labjavaspringsecurity.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BlogPostUpdateRequest(
        @NotNull Long authorId,
        @NotBlank String title,
        @NotBlank String post
) {
}

