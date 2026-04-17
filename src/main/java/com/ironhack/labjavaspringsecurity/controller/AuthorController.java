package com.ironhack.labjavaspringsecurity.controller;

import com.ironhack.labjavaspringsecurity.dto.AuthorCreateRequest;
import com.ironhack.labjavaspringsecurity.dto.AuthorUpdateRequest;
import com.ironhack.labjavaspringsecurity.exception.NotFoundException;
import com.ironhack.labjavaspringsecurity.model.Author;
import com.ironhack.labjavaspringsecurity.repository.AuthorRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorRepository authorRepository;

    public AuthorController(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @GetMapping("/{id}")
    public Author getById(@PathVariable Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Author not found: " + id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Author create(@Valid @RequestBody AuthorCreateRequest request) {
        var author = new Author();
        author.setName(request.name());
        return authorRepository.save(author);
    }

    @PutMapping("/{id}")
    public Author update(@PathVariable Long id, @Valid @RequestBody AuthorUpdateRequest request) {
        var author = authorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Author not found: " + id));
        author.setName(request.name());
        return authorRepository.save(author);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        if (!authorRepository.existsById(id)) {
            throw new NotFoundException("Author not found: " + id);
        }
        authorRepository.deleteById(id);
    }
}

