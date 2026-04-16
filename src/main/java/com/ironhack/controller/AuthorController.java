package com.ironhack.controller;

import com.ironhack.dto.request.CreateAuthorRequest;
import com.ironhack.dto.request.UpdateAuthorRequest;
import com.ironhack.dto.response.AuthorResponse;
import com.ironhack.service.AuthorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/authors")
public class AuthorController {
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public ResponseEntity<List<AuthorResponse>> getAllAuthors() {
        return ResponseEntity.ok(authorService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorResponse> getAuthorById(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(authorService.getById(id));
    }

    @PostMapping
    public ResponseEntity<AuthorResponse> createAuthor(
            @Valid @RequestBody CreateAuthorRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authorService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthorResponse> updateAuthor(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateAuthorRequest request
    ) {
        return ResponseEntity.ok(authorService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(
            @PathVariable UUID id
    ) {
        authorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
