package com.ironhack.dto.response;

import java.util.UUID;

public record PostResponse(
        UUID id,
        String title,
        String post,
        AuthorResponse author
) {}
