package org.example.spring_security.Service;

import org.example.spring_security.Dto.AuthorRequest;
import org.example.spring_security.Dto.AuthorResponse;
import org.example.spring_security.Entity.Author;
import org.example.spring_security.Exception.ResourceNotFoundException;
import org.example.spring_security.Repository.AuthorRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public AuthorResponse getAuthor(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + id));

        return new AuthorResponse(author.getId(), author.getName());
    }

    public AuthorResponse addAuthor(AuthorRequest request) {
        Author author = new Author(request.getName());
        Author saved = authorRepository.save(author);
        return new AuthorResponse(saved.getId(), saved.getName());
    }

    public AuthorResponse updateAuthor(Long id, AuthorRequest request) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + id));

        author.setName(request.getName());
        Author updated = authorRepository.save(author);

        return new AuthorResponse(updated.getId(), updated.getName());
    }

    public void deleteAuthor(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + id));
        authorRepository.delete(author);
    }
}
