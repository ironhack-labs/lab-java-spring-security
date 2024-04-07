package com.example.springsecurity.demo.repositories;

import com.example.springsecurity.demo.model.BlogPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BlogPostRepository extends JpaRepository<BlogPost, String> {

    BlogPost findByTitle(String title);

    BlogPost findById(Integer id);

    @Override
    List<BlogPost> findAll();


    @Override
    List<BlogPost> findAllById(Iterable<String> strings);

    @Query("SELECT p FROM BlogPost p WHERE p.authorId.authorId= :authorId")
    List<BlogPost> findAllByAuthorId(@Param("authorId") Integer id);
}
