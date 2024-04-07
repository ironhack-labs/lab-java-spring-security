package com.example.springsecurity.demo.controllers;

import com.example.springsecurity.demo.model.Author;
import com.example.springsecurity.demo.service.impl.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthorController {
    @Autowired
    private AuthorService authorService;

    @PostMapping("/Author")
    public Author saveAuthor(Author author){
        return authorService.saveAuthor(author);
    }

    @PutMapping("/Author/update")
    public Author updateAuthor(Author author){
        return authorService.updateAuthor(author);
    }

    @DeleteMapping("/Author/delete")

    public Author deleteAuthor(Author author){
        return authorService.deleteAuthor(author);
    }

}
