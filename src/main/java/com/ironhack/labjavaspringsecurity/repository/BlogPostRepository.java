package com.ironhack.labjavaspringsecurity.repository;

import com.ironhack.labjavaspringsecurity.model.BlogPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogPostRepository extends JpaRepository<BlogPost, Long> {
}

