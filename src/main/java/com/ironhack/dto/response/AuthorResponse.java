package com.ironhack.dto.response;

import java.util.UUID;

public record AuthorResponse(
        UUID id,
        String name
) {}
