package com.ironhack.demo.controller;

import com.ironhack.demo.dto.AuthorRequest;
import com.ironhack.demo.entity.Author;
import com.ironhack.demo.service.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public List<Author> findAll(){
        return authorService.findAll();
    }

    @GetMapping("/{id}")
    public Author findById(@PathVariable Long id){
        return authorService.findById(id);
    }

    @GetMapping("/email")
    public Author findByEmail(@RequestParam String email){
        return authorService.findByEmail(email);
    }

    @PutMapping("/{id}")
    public Author update(@PathVariable Long id, @RequestBody AuthorRequest request){
        authorService.findById(id);
        return authorService.update(id, request);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        authorService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
