package com.ironhack.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UpdatePostRequest(
        @NotBlank(message = "Title is required")
        String title,
        @NotBlank(message = "Post content is required")
        String post,
        @NotNull(message = "Author id is required")
        UUID authorId
) {}
