package com.ironhack.lab4_1_spring_security.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "Blog Post")
public class BlogPost {
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,length = 300)
    private String title;

    @Column(nullable = false,length = 300)
    private String post;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    public BlogPost(Long id, String title, String post) {
        this.id = id;
        this.title = title;
        this.post = post;
    }
    public BlogPost() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }
}
