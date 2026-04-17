package com.ironhack.lab4_1_spring_security.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Author")
public class Author {
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (nullable = false,length = 100)
    private String name;

    @OneToMany(mappedBy = "author")
    private List<BlogPost> posts;

    public Author(Long id, String name) {
        this.id = id;
        this.name = name;
    }
    public Author(){
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
