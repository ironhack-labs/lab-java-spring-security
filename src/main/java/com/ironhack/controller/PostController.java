package com.ironhack.controller;

import com.ironhack.dto.request.CreatePostRequest;
import com.ironhack.dto.request.UpdatePostRequest;
import com.ironhack.dto.response.PostResponse;
import com.ironhack.service.PostService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        return ResponseEntity.ok(postService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPostById(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(postService.getById(id));
    }

    @PostMapping
    public ResponseEntity<PostResponse> createPost(
            @Valid @RequestBody CreatePostRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(postService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostResponse> updatePost(
            @PathVariable UUID id,
            @Valid @RequestBody UpdatePostRequest request
    ) {
        return ResponseEntity.ok(postService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable UUID id) {
        postService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
