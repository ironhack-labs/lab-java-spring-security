package com.ironhack.labjavaspringsecurity.controller;

import com.ironhack.labjavaspringsecurity.dto.BlogPostCreateRequest;
import com.ironhack.labjavaspringsecurity.dto.BlogPostUpdateRequest;
import com.ironhack.labjavaspringsecurity.exception.NotFoundException;
import com.ironhack.labjavaspringsecurity.model.BlogPost;
import com.ironhack.labjavaspringsecurity.repository.AuthorRepository;
import com.ironhack.labjavaspringsecurity.repository.BlogPostRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/posts")
public class BlogPostController {

    private final BlogPostRepository blogPostRepository;
    private final AuthorRepository authorRepository;

    public BlogPostController(BlogPostRepository blogPostRepository, AuthorRepository authorRepository) {
        this.blogPostRepository = blogPostRepository;
        this.authorRepository = authorRepository;
    }

    @GetMapping("/{id}")
    public BlogPost getById(@PathVariable Long id) {
        return blogPostRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Post not found: " + id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BlogPost create(@Valid @RequestBody BlogPostCreateRequest request) {
        var author = authorRepository.findById(request.authorId())
                .orElseThrow(() -> new NotFoundException("Author not found: " + request.authorId()));

        var post = new BlogPost();
        post.setAuthor(author);
        post.setTitle(request.title());
        post.setPost(request.post());
        return blogPostRepository.save(post);
    }

    @PutMapping("/{id}")
    public BlogPost update(@PathVariable Long id, @Valid @RequestBody BlogPostUpdateRequest request) {
        var post = blogPostRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Post not found: " + id));
        var author = authorRepository.findById(request.authorId())
                .orElseThrow(() -> new NotFoundException("Author not found: " + request.authorId()));

        post.setAuthor(author);
        post.setTitle(request.title());
        post.setPost(request.post());
        return blogPostRepository.save(post);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        if (!blogPostRepository.existsById(id)) {
            throw new NotFoundException("Post not found: " + id);
        }
        blogPostRepository.deleteById(id);
    }
}

