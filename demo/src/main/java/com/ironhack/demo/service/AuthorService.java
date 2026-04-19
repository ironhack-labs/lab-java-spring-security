package com.ironhack.demo.service;


import com.ironhack.demo.dto.AuthorRequest;
import com.ironhack.demo.entity.Author;
import com.ironhack.demo.repository.AuthorRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthorService(AuthorRepository authorRepository, PasswordEncoder passwordEncoder) {
        this.authorRepository = authorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Author findByEmail(String email){
        return authorRepository.findByEmail(email).orElseThrow(() ->
                new NullPointerException("Author not found!"));
    }

    public List<Author> findAll(){
        return authorRepository.findAll();
    }

    public Author findById(Long id){
        return authorRepository.findById(id).orElseThrow(() -> new NullPointerException("Author not found"));
    }

    @Transactional
    public Author register(AuthorRequest request){
        if(authorRepository.existsByEmail(request.getEmail())){
            throw new IllegalArgumentException("An author with this email already exists");
        }
        Author createdAuthor = new Author();
        createdAuthor.setFullName(request.getFullName());
        createdAuthor.setEmail(request.getEmail());
        createdAuthor.setPassword(passwordEncoder.encode(request.getPassword()));
        return authorRepository.save(createdAuthor);
    }

    @Transactional
    public Author update(Long id, AuthorRequest request){
        Author author = authorRepository.findById(id).orElseThrow(() -> new RuntimeException("Author not found"));
        if(request.getEmail() != null){
            author.setEmail(request.getEmail());
        }
        if(request.getFullName() != null){
            author.setFullName(request.getFullName());
        }
        if(request.getPassword() != null){
            author.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        authorRepository.save(author);
        return author;
    }

    public void delete(Long id){
        Author author = authorRepository.findById(id).orElseThrow(() -> new RuntimeException("Author not found"));
        authorRepository.delete(author);
    }
}
