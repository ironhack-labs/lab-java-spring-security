package com.ironhack.springsecuritylab.repository;

import com.ironhack.springsecuritylab.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Author findAuthorById(Long id);

    Author getAuthorById(Long id);
}
