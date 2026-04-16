package com.ironhack.mapper;

import com.ironhack.dto.request.CreateAuthorRequest;
import com.ironhack.dto.response.AuthorResponse;
import com.ironhack.entity.Author;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapper {
    public Author toEntity(CreateAuthorRequest request) {
        return new Author(
                request.name()
        );
    }

    public AuthorResponse toResponse(Author author) {
        return new AuthorResponse(
                author.getId(),
                author.getName()
        );
    }
}
