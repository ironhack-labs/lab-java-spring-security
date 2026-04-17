package com.ironhack.springsecuritylab.dto.request;

import jakarta.validation.constraints.NotBlank;

public class AuthorUpdateRequest {

    @NotBlank
    private Long id;

    @NotBlank(message = "Full name is required")
    private String name;

    public AuthorUpdateRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
