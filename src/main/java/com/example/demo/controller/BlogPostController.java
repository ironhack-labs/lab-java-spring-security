package com.example.demo.controller;

import com.example.demo.dto.BlogPostRequest;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Author;
import com.example.demo.model.BlogPost;
import com.example.demo.repository.AuthorRepository;
import com.example.demo.repository.BlogPostRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class BlogPostController {

    private final BlogPostRepository blogPostRepository;
    private final AuthorRepository authorRepository;

    public BlogPostController(BlogPostRepository blogPostRepository, AuthorRepository authorRepository) {
        this.blogPostRepository = blogPostRepository;
        this.authorRepository = authorRepository;
    }

    @GetMapping
    public List<BlogPost> getAllPosts() {
        return blogPostRepository.findAll();
    }

    @GetMapping("/{id}")
    public BlogPost getPostById(@PathVariable Long id) {
        return blogPostRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BlogPost createPost(@Valid @RequestBody BlogPostRequest request) {
        Author author = authorRepository.findById(request.getAuthorId())
                .orElseThrow(() -> new ResourceNotFoundException("Author not found"));

        BlogPost post = new BlogPost();
        post.setTitle(request.getTitle());
        post.setPost(request.getPost());
        post.setAuthor(author);

        return blogPostRepository.save(post);
    }

    @PutMapping("/{id}")
    public BlogPost updatePost(@PathVariable Long id, @Valid @RequestBody BlogPostRequest request) {
        BlogPost existing = blogPostRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));
        Author author = authorRepository.findById(request.getAuthorId())
                .orElseThrow(() -> new ResourceNotFoundException("Author not found"));

        existing.setTitle(request.getTitle());
        existing.setPost(request.getPost());
        existing.setAuthor(author);
        return blogPostRepository.save(existing);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePost(@PathVariable Long id) {
        BlogPost existing = blogPostRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));
        blogPostRepository.delete(existing);
    }
}
