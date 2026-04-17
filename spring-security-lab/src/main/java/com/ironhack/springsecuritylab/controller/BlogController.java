package com.ironhack.springsecuritylab.controller;

import com.ironhack.springsecuritylab.dto.request.AuthorRequest;
import com.ironhack.springsecuritylab.dto.request.AuthorUpdateRequest;
import com.ironhack.springsecuritylab.dto.request.BlogRequest;
import com.ironhack.springsecuritylab.dto.request.BlogUpdateRequest;
import com.ironhack.springsecuritylab.dto.response.AuthorResponse;
import com.ironhack.springsecuritylab.dto.response.BlogResponse;
import com.ironhack.springsecuritylab.entity.Author;
import com.ironhack.springsecuritylab.entity.Blog;
import com.ironhack.springsecuritylab.service.AuthorService;
import com.ironhack.springsecuritylab.service.BlogService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/blog")
public class BlogController {

    private final BlogService blogService;

    public BlogController(BlogService blogService){
        this.blogService = blogService;
    }

    @GetMapping
    public ResponseEntity<List<Blog>> getAllBlogs(){
        List<Blog> response = blogService.getAllBlogs();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @GetMapping("/{id}")
    public ResponseEntity<BlogResponse> getBlog(@PathVariable Long id){
        BlogResponse response = blogService.getBlogByid(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @PostMapping
    public ResponseEntity<BlogResponse> createBlog(@Valid @RequestBody BlogRequest request){
        BlogResponse response = blogService.createBlog(request.getTitle(), request.getPost(), request.getAuthorId());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @PutMapping
    public ResponseEntity<BlogResponse> updateBlog(@Valid  @RequestBody BlogUpdateRequest request){
        BlogResponse response = blogService.updateBlog(request.getId(), request.getTitle(), request.getPost(), request.getAuthorId());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id){
        blogService.deleteBlog(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
