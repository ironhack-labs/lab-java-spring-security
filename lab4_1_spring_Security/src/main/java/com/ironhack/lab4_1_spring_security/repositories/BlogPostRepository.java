package com.ironhack.lab4_1_spring_security.repositories;

import com.ironhack.lab4_1_spring_security.entities.BlogPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogPostRepository extends JpaRepository<BlogPost,Long> {

}
