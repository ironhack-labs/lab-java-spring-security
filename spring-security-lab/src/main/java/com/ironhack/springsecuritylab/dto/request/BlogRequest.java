package com.ironhack.springsecuritylab.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class BlogRequest {

    @NotBlank(message = "Title cannot be empty")
    @Size(max = 250, message = "Title cannot exceed 250 characters")
    private String title;

    @NotBlank(message = "Post cannot be empty")
    @Size(max = 2000, message = "Post cannot exceed 2000 characters")
    private String post;

    @NotNull(message = "Author ID is required")
    private Long authorId;

    public BlogRequest() {}

    public String getTitle() {
        return title;
    }

    public String getPost() {
        return post;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }
}