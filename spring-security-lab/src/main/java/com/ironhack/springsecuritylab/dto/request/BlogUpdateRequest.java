package com.ironhack.springsecuritylab.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class BlogUpdateRequest {

    @NotNull(message = "Blog ID is required")
    private Long id;

    @Size(max = 250, message = "Title cannot exceed 250 characters")
    private String title;

    @Size(max = 2000, message = "Post cannot exceed 2000 characters")
    private String post;

    @NotNull(message = "Author ID is required")
    private Long authorId;
    public BlogUpdateRequest() {}

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPost() {
        return post;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }
}