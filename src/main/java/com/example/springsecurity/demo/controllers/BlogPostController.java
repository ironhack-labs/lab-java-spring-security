package com.example.springsecurity.demo.controllers;

import com.example.springsecurity.demo.model.Author;
import com.example.springsecurity.demo.model.BlogPost;
import com.example.springsecurity.demo.service.impl.BlogPostService;
import com.example.springsecurity.demo.service.interfaces.BlogPostServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BlogPostController {
    @Autowired
    private BlogPostService blogPostService;

    @PostMapping("/BlogPost")
    public BlogPost saveBlogPost(BlogPost blog){
        return blogPostService.publishBlogPost(blog);
    }

    @PutMapping("/BlogPost/update")
    public Author updateBlog(BlogPost blog){
        return blogPostService.updateBlogPost(blog).getAuthorId();
    }

    @DeleteMapping("/BlogPost/delete")
    public Author deleteAuthor(BlogPost blog){
        return blogPostService.deleteBlogPost(blog).getAuthorId();
    }

}
