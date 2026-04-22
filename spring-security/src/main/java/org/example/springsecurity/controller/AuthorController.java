package org.example.springsecurity.controller;

import org.example.springsecurity.entity.Author;
import org.example.springsecurity.repository.AuthorRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {
    private final AuthorRepository authorRepository;
    public AuthorController(AuthorRepository authorRepository){
        this.authorRepository=authorRepository;
    }
    @GetMapping
    public List<Author> findByAuthor(){
        return authorRepository.findAll();
    }
    @PostMapping
    public Author addAuthor(@RequestBody Author author){
        return authorRepository.save(author);
    }
    @PutMapping("/{id}")
    public Author updateById(@PathVariable Long id,@RequestBody Author oldAuthor){
        Author newAuthor=authorRepository.findById(id).orElseThrow(()->new RuntimeException());
        newAuthor.setName(oldAuthor.getName());
        return authorRepository.save(newAuthor);
    }
    @DeleteMapping("/{id}")
    public void deleteAuthor(@PathVariable Long id){
        authorRepository.deleteById(id);
    }

}
