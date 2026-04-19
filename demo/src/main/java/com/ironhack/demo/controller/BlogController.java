package com.ironhack.demo.controller;


import com.ironhack.demo.dto.BlogRequest;
import com.ironhack.demo.entity.Blog;
import com.ironhack.demo.repository.AuthorRepository;
import com.ironhack.demo.repository.BlogRepository;
import com.ironhack.demo.service.BlogService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/blogs")
public class BlogController {
    private final BlogService blogService;
    private final AuthorRepository authorRepository;

    public BlogController(BlogService blogService, AuthorRepository authorRepository) {
        this.blogService = blogService;
        this.authorRepository = authorRepository;
    }

    @GetMapping
    public List<Blog> findAll(){
        return blogService.findAll();
    }

    @GetMapping("/{id}")
    public Blog findById(@PathVariable Long id){
        return blogService.findById(id);
    }

    @PostMapping
    public ResponseEntity<Blog> create(@Valid @RequestBody BlogRequest request){
        Blog createdBlog = blogService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBlog);
    }

    @PreAuthorize("@blogService.isOwner(#id, authentication.name) or hasRole('ADMIN')")
    @PutMapping("/{id}")
    public Blog update(@PathVariable Long id, @RequestBody BlogRequest request){
        return blogService.update(id, request);
    }

    @PreAuthorize("@blogService.isOwner(#id, authentication.name) or hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        blogService.findById(id);
        blogService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
