package com.ironhack.springsecuritylab.controller;

import com.ironhack.springsecuritylab.dto.request.AuthorRequest;
import com.ironhack.springsecuritylab.dto.request.AuthorUpdateRequest;
import com.ironhack.springsecuritylab.dto.response.AuthorResponse;
import com.ironhack.springsecuritylab.dto.response.BlogResponse;
import com.ironhack.springsecuritylab.entity.Author;
import com.ironhack.springsecuritylab.service.AuthorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/author")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService){
        this.authorService = authorService;
    }
    @GetMapping
    public ResponseEntity<List<Author>> getAllAuthors(){
        List<Author> response = authorService.getAllAuthors();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @GetMapping("/{id}")
    public ResponseEntity<AuthorResponse> getAuthor(@PathVariable Long id){
        AuthorResponse response = authorService.getAuthorByid(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @PostMapping
    public ResponseEntity<AuthorResponse> createAuthor(@Valid @RequestBody AuthorRequest request){
        AuthorResponse response = authorService.createAuthor(request.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @PutMapping
    public ResponseEntity<AuthorResponse> updateAuthor(@Valid @RequestBody AuthorUpdateRequest request){
        AuthorResponse response = authorService.updateAuthor(request.getId(), request.getName());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id){
        authorService.deleteAuthor(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
