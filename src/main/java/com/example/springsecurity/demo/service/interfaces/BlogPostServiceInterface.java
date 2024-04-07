package com.example.springsecurity.demo.service.interfaces;

import com.example.springsecurity.demo.model.BlogPost;

public interface BlogPostServiceInterface {
    BlogPost publishBlogPost (BlogPost blog);

    BlogPost updateBlogPost (BlogPost blog);

    BlogPost deleteBlogPost (BlogPost blog);
}
