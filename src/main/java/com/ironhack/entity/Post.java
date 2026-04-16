package com.ironhack.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String post;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    public Post() {}
    public Post(String title, String post, Author author) {
        this.title = title;
        this.post = post;
        this.author = author;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
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

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}
