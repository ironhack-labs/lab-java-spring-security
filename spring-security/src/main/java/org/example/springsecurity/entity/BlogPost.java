package org.example.springsecurity.entity;

import jakarta.persistence.*;

@Entity
@Table(name="blog_post")
public class BlogPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne()
    @JoinColumn(name="author_id")
    private Author author;
    @Column(nullable = false, length=100)
    private String title;
    @Column(nullable = false,length=500)
    private String post;
    public BlogPost(){}
    public BlogPost(Author author, String title, String post){
        this.author=author;
        this.title=title;
        this.post=post;
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

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}
