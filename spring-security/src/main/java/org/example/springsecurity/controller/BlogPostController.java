package org.example.springsecurity.controller;

import org.example.springsecurity.entity.BlogPost;
import org.example.springsecurity.repository.BlogPostRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class BlogPostController {
    private final BlogPostRepository blogPostRepository;
    public BlogPostController(BlogPostRepository blogPostRepository){
        this.blogPostRepository=blogPostRepository;
    }
    @GetMapping
    public List<BlogPost> findByPost(){
        return blogPostRepository.findAll();
    }
    @PostMapping
    public BlogPost addPost(@RequestBody BlogPost blogPost){
        return blogPostRepository.save(blogPost);
    }
    @PutMapping("/{id}")
    public BlogPost updatePost(@PathVariable Long id,@RequestBody BlogPost oldPost){
        BlogPost newPost= blogPostRepository.findById(id).orElseThrow(()->new RuntimeException());
        newPost.setPost(oldPost.getPost());
        newPost.setTitle(oldPost.getTitle());
        return blogPostRepository.save(newPost);
    }
    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable Long id){
        blogPostRepository.deleteById(id);
    }

}
