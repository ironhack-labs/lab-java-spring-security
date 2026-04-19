package org.example.spring_security.Controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.example.spring_security.Dto.PostRequest;
import org.example.spring_security.Dto.PostResponse;
import org.example.spring_security.Service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @Operation(summary = "Get post by id with author")
    @GetMapping("/{id}")
    public PostResponse getPost(@PathVariable Long id) {
        return postService.getPost(id);
    }

    @Operation(summary = "Add post")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostResponse addPost(@Valid @RequestBody PostRequest request) {
        return postService.addPost(request);
    }

    @Operation(summary = "Update post")
    @PutMapping("/{id}")
    public PostResponse updatePost(@PathVariable Long id, @Valid @RequestBody PostRequest request) {
        return postService.updatePost(id, request);
    }

    @Operation(summary = "Delete post")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePost(@PathVariable Long id) {
        postService.deletePost(id);
    }
}
