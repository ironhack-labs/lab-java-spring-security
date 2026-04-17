package com.ironhack.lab4_1_spring_security.controllers;

import com.ironhack.lab4_1_spring_security.entities.BlogPost;
import com.ironhack.lab4_1_spring_security.repositories.BlogPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class BlogPostController {

    @Autowired
    private BlogPostRepository blogPostRepository;

    @GetMapping
    public List<BlogPost> getAllPosts() {
        return blogPostRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BlogPost addPost(@RequestBody BlogPost post) {
        return blogPostRepository.save(post);
    }

    @PutMapping("/{id}")
    public BlogPost updatePost(@PathVariable Long id, @RequestBody BlogPost postDetails) {
        BlogPost post = blogPostRepository.findById(id).orElseThrow();
        post.setTitle(postDetails.getTitle());
        post.setPost(postDetails.getPost());
        return blogPostRepository.save(post);
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable Long id) {
        blogPostRepository.deleteById(id);
    }
}