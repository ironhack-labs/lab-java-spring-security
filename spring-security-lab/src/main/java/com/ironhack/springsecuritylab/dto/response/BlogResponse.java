package com.ironhack.springsecuritylab.dto.response;

import com.ironhack.springsecuritylab.entity.Author;
import jakarta.persistence.*;

public class BlogResponse {
    private Long id;
    private String title;
    private String post;
    private Author author;

    public BlogResponse() {
    }

    public BlogResponse(Long id, String title, String post, Author author) {
        this.id = id;
        this.title = title;
        this.post = post;
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPost() {
        return post;
    }

    public Author getAuthor() {
        return author;
    }
}
