package com.example.springsecurity.demo.repositories;

import com.example.springsecurity.demo.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, String> {
    Author findByAuthorId(Integer id);

    Author findByAuthorName(String name);

    List<Author> findAll();


}
