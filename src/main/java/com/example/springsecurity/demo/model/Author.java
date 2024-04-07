package com.example.springsecurity.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "author_table")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer authorId;

    @Column(name = "name")
    private String authorName;

    @OneToMany(mappedBy = "id", cascade = CascadeType.ALL)
    private List<BlogPost> blogPost = new ArrayList<>();

}
