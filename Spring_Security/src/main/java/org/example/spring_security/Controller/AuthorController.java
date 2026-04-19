package org.example.spring_security.Controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.example.spring_security.Dto.AuthorRequest;
import org.example.spring_security.Dto.AuthorResponse;
import org.example.spring_security.Service.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @Operation(summary = "Get author by id")
    @GetMapping("/{id}")
    public AuthorResponse getAuthor(@PathVariable Long id) {
        return authorService.getAuthor(id);
    }

    @Operation(summary = "Add author")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AuthorResponse addAuthor(@Valid @RequestBody AuthorRequest request) {
        return authorService.addAuthor(request);
    }

    @Operation(summary = "Update author")
    @PutMapping("/{id}")
    public AuthorResponse updateAuthor(@PathVariable Long id, @Valid @RequestBody AuthorRequest request) {
        return authorService.updateAuthor(id, request);
    }

    @Operation(summary = "Delete author")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
    }
}