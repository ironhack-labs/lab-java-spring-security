package com.example.springsecurity.demo.service.impl;

import com.example.springsecurity.demo.model.Author;
import com.example.springsecurity.demo.model.BlogPost;
import com.example.springsecurity.demo.repositories.AuthorRepository;
import com.example.springsecurity.demo.service.interfaces.AuthorServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class AuthorService implements AuthorServiceInterface {

    @Autowired
    private AuthorRepository authorRepository;


    @Override
    public Author saveAuthor(Author author) {
        authorRepository.save(author);
        return null;
    }

    @Override
    public Author updateAuthor(Author author) {
        Author existingAuthor = authorRepository.findByAuthorId(author.getAuthorId());

        if (existingAuthor != null){
            existingAuthor.setAuthorName(author.getAuthorName());
        }

        authorRepository.save(existingAuthor);

        return null;
    }

    @Override
    public Author deleteAuthor(Author author) {
        if (author != null){
            authorRepository.delete(author);
        } else {
            try {
                throw new IOException("No Author to delete");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return author;
    }
}
