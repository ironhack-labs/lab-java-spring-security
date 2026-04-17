package com.ironhack.springsecuritylab.service;

import com.ironhack.springsecuritylab.dto.request.AuthorRequest;
import com.ironhack.springsecuritylab.dto.response.AuthorResponse;
import com.ironhack.springsecuritylab.entity.Author;
import com.ironhack.springsecuritylab.repository.AuthorRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository){
        this.authorRepository = authorRepository;
    }

    @Transactional
    public AuthorResponse createAuthor(String name){
        Author author = new Author();
        author.setName(name);
        author = authorRepository.save(author);
        return new AuthorResponse(author.getId(),
                author.getName());
    }

    @Transactional
    public AuthorResponse updateAuthor(Long id, String name){
        Author author = authorRepository.findAuthorById(id);
        author.setName(name);
        author = authorRepository.save(author);
        return new AuthorResponse(
                author.getId(),
                author.getName()
        );
    }

    @Transactional
    public void deleteAuthor(Long id){
        authorRepository.deleteById(id);
    }

    public AuthorResponse getAuthorByid(Long id){
        Author author = authorRepository.getAuthorById(id);
        return new AuthorResponse(
                author.getId(),
                author.getName()
        );
    }

    public List<Author> getAllAuthors(){
        return authorRepository.findAll();
    }
}
