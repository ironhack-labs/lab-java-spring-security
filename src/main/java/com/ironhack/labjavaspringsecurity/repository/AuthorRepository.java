package com.ironhack.labjavaspringsecurity.repository;

import com.ironhack.labjavaspringsecurity.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}

