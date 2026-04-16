package com.ironhack.service;

import com.ironhack.dto.request.CreateAuthorRequest;
import com.ironhack.dto.request.UpdateAuthorRequest;
import com.ironhack.dto.response.AuthorResponse;
import com.ironhack.entity.Author;
import com.ironhack.exception.NotFoundException;
import com.ironhack.mapper.AuthorMapper;
import com.ironhack.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    public AuthorService(AuthorRepository authorRepository, AuthorMapper authorMapper) {
        this.authorRepository = authorRepository;
        this.authorMapper = authorMapper;
    }

    public List<AuthorResponse> getAll() {
        return authorRepository.findAll().stream()
                .map(authorMapper::toResponse)
                .toList();
    }

    public AuthorResponse getById(UUID authorId) {
        Author author = findOrThrow(authorId);
        return authorMapper.toResponse(author);
    }

    public AuthorResponse create(CreateAuthorRequest request) {
        Author savedAuthor = authorRepository.save(authorMapper.toEntity(request));
        return authorMapper.toResponse(savedAuthor);
    }

    public AuthorResponse update(UUID authorId, UpdateAuthorRequest request) {
        Author author = findOrThrow(authorId);
        author.setName(request.name());

        Author updatedAuthor = authorRepository.save(author);
        return authorMapper.toResponse(updatedAuthor);
    }

    public void delete(UUID authorId) {
        Author author = findOrThrow(authorId);
        authorRepository.delete(author);
    }

    public Author findOrThrow(UUID authorId) {
        return authorRepository.findById(authorId)
                .orElseThrow(() -> new NotFoundException("Author not found: " + authorId));
    }
}
