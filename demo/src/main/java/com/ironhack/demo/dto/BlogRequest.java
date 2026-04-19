package com.ironhack.demo.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Size;

public class BlogRequest {

    @Size(max = 100)
    private String title;

    private Long authorId;

    @Size(max = 1000, min = 1)
    private String content;

    public BlogRequest() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
