package com.ironhack.demo.controller;


import com.ironhack.demo.dto.AuthorRequest;
import com.ironhack.demo.entity.Author;
import com.ironhack.demo.repository.AuthorRepository;
import com.ironhack.demo.service.AuthorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final PasswordEncoder passwordEncoder;
    private final AuthorService authorService;

    public AuthController(PasswordEncoder passwordEncoder, AuthorService authorService) {
        this.passwordEncoder = passwordEncoder;
        this.authorService = authorService;
    }

    @PostMapping("/register")
    public ResponseEntity<Author> register(@Valid @RequestBody AuthorRequest request){
        Author author = authorService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(author);
    }
}
