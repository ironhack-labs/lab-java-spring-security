package com.ironhack.springsecuritylab.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class AuthorRequest {

    @NotBlank(message = "Full name is required")
    private String name;

    public AuthorRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
