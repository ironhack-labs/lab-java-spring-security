package com.ironhack.lab4_1_spring_security.repositories;

import com.ironhack.lab4_1_spring_security.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author,Long> {

}
