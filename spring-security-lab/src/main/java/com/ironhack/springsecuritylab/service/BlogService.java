package com.ironhack.springsecuritylab.service;

import com.ironhack.springsecuritylab.dto.response.AuthorResponse;
import com.ironhack.springsecuritylab.dto.response.BlogResponse;
import com.ironhack.springsecuritylab.entity.Author;
import com.ironhack.springsecuritylab.entity.Blog;
import com.ironhack.springsecuritylab.repository.AuthorRepository;
import com.ironhack.springsecuritylab.repository.BlogRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BlogService {

    private final BlogRepository blogRepository;
    private final AuthorRepository authorRepository;
    public BlogService(BlogRepository blogRepository,
                       AuthorRepository authorRepository){
        this.blogRepository = blogRepository;
        this.authorRepository = authorRepository;
    }

    @Transactional
    public BlogResponse createBlog(String title, String post, Long authorId){
        Blog blog = new Blog();
        blog.setTitle(title);
        blog.setPost(post);
        blog.setAuthor(authorRepository.findAuthorById(authorId));
        blog = blogRepository.save(blog);
        return new BlogResponse(blog.getId(),
                blog.getTitle(),
                blog.getPost(),
                blog.getAuthor()
        );
    }

    @Transactional
    public BlogResponse updateBlog(Long id, String title, String post, Long authorId){
        Blog blog = blogRepository.findBlogById(id);
        blog.setTitle(title);
        blog.setPost(post);
        blog.setAuthor(authorRepository.findAuthorById(authorId));
        return new BlogResponse(blog.getId(),
                blog.getTitle(),
                blog.getPost(),
                blog.getAuthor()
        );
    }

    @Transactional
    public void deleteBlog(Long id){
        blogRepository.deleteById(id);
    }

    public BlogResponse getBlogByid(Long id){
        Blog blog = blogRepository.findBlogById(id);
        return new BlogResponse(
                blog.getId(),
                blog.getTitle(),
                blog.getPost(),
                blog.getAuthor()
        );
    }

    public List<Blog> getAllBlogs(){
        return blogRepository.findAll();
    }
}
