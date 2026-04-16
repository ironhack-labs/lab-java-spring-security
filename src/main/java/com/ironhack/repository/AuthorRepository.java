package com.ironhack.repository;

import com.ironhack.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AuthorRepository extends JpaRepository<Author, UUID> {
}
