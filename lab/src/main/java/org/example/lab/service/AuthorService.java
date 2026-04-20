package org.example.lab.service;

import org.example.lab.dto.request.AuthorRequest;
import org.example.lab.dto.response.AuthorResponse;
import org.example.lab.mapper.AuthorMapper;
import org.example.lab.model.Author;
import org.example.lab.repository.AuthorRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public AuthorResponse createAuthor(AuthorRequest request){
        Author author = AuthorMapper.toAuthor(request);
        Author authorSaved = authorRepository.save(author);
        return AuthorMapper.toResponse(authorSaved);
    }

    public AuthorResponse updateAuthor(Long id, AuthorRequest request){
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found"));
        author.setName(request.getName());

        Author updateAuthor = authorRepository.save(author);
        return AuthorMapper.toResponse(updateAuthor);
    }
    public  void deleteAuthor(Long id){
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found"));
        authorRepository.delete(author);
    }
}
