package com.ironhack.demo.service;


import com.ironhack.demo.dto.AuthorRequest;
import com.ironhack.demo.dto.BlogRequest;
import com.ironhack.demo.entity.Author;
import com.ironhack.demo.entity.Blog;
import com.ironhack.demo.repository.AuthorRepository;
import com.ironhack.demo.repository.BlogRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogService {

    private final BlogRepository blogRepository;
    private final AuthorRepository authorRepository;

    public BlogService(BlogRepository blogRepository, AuthorRepository authorRepository) {
        this.blogRepository = blogRepository;
        this.authorRepository = authorRepository;
    }

    public Blog findById(Long id){
        return blogRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Blog not found"));
    }

    public List<Blog> findAll(){
        return blogRepository.findAll();
    }

    @Transactional
    public Blog create(BlogRequest request){
        Blog createdBlog = new Blog();
        Author foundAuthor = authorRepository.findById(request.getAuthorId())
                .orElseThrow(() -> new IllegalArgumentException("Author not found"));

        createdBlog.setTitle(request.getTitle());
        createdBlog.setAuthor(foundAuthor);
        createdBlog.setContent(request.getContent());
        return blogRepository.save(createdBlog);
    }

    @Transactional
    public Blog update(Long id, BlogRequest request){
        Blog foundBlog = blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Blog not found"));

        if(request.getTitle() != null){
            foundBlog.setTitle(request.getTitle());
        }
        if(request.getAuthorId() != null){
            Author foundAuthor = authorRepository.findById(request.getAuthorId())
                    .orElseThrow(() -> new IllegalArgumentException("Author not found"));

            foundBlog.setAuthor(foundAuthor);
        }
        if(request.getContent() != null){
            foundBlog.setContent(request.getContent());
        }

        return blogRepository.save(foundBlog);
    }

    public void delete(Long id){
        Blog foundBlog = blogRepository.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("Blog not found"));
        blogRepository.delete(foundBlog);
    }

    public boolean isOwner(Long blogId, String email){
        Blog foundBlog = blogRepository.findById(blogId)
                .orElseThrow(() -> new IllegalArgumentException("Blog not found"));
        return foundBlog.getAuthor().getEmail().equals(email);
    }
}
