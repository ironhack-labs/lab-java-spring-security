package com.example.springsecurity.demo.service.impl;

import com.example.springsecurity.demo.model.BlogPost;
import com.example.springsecurity.demo.repositories.BlogPostRepository;
import com.example.springsecurity.demo.service.interfaces.BlogPostServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class BlogPostService implements BlogPostServiceInterface {

    @Autowired
    private BlogPostRepository blogPostRepository;


    @Override
    public BlogPost publishBlogPost(BlogPost blog) {
        return blogPostRepository.save(blog);
    }

    @Override
    public BlogPost updateBlogPost(BlogPost blog) {
        BlogPost existingBlog = blogPostRepository.findById(blog.getId());

        if (existingBlog != null){
            existingBlog.setTitle(blog.getTitle());
            existingBlog.setPost(blog.getPost());
        }

        blogPostRepository.save(existingBlog);

        return existingBlog;
    }

    @Override
    public BlogPost deleteBlogPost(BlogPost blog) {
        if (blog != null){
            blogPostRepository.delete(blog);
        } else {
            try {
                throw new IOException("No Blog to delete");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return blog;
    }
}
