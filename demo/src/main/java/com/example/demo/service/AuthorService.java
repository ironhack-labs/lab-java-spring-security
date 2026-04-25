package com.example.demo.service;

import com.example.demo.entities.Author;
import com.example.demo.repositories.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepository;

    public List<Author> getAuthors() {
        return authorRepository.findAll();
    }

    public Author addAuthor(Author author) {
        return authorRepository.save(author);
    }

    public Author updateAuthor(Long id, Author author) {
        Author kohneAuthor = authorRepository.findById(id).orElseThrow();
        kohneAuthor.setName(author.getName());
        return authorRepository.save(kohneAuthor);
    }

    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }


}
