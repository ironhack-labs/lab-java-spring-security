package org.example.spring_security.Service;

import org.example.spring_security.Dto.PostRequest;
import org.example.spring_security.Dto.PostResponse;
import org.example.spring_security.Entity.Author;
import org.example.spring_security.Entity.BlogPost;
import org.example.spring_security.Exception.ResourceNotFoundException;
import org.example.spring_security.Repository.AuthorRepository;
import org.example.spring_security.Repository.BlogPostRepository;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    private final BlogPostRepository blogPostRepository;
    private final AuthorRepository authorRepository;

    public PostService(BlogPostRepository blogPostRepository, AuthorRepository authorRepository) {
        this.blogPostRepository = blogPostRepository;
        this.authorRepository = authorRepository;
    }

    public PostResponse getPost(Long id) {
        BlogPost post = blogPostRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + id));

        return mapToResponse(post);
    }

    public PostResponse addPost(PostRequest request) {
        Author author = authorRepository.findById(request.getAuthorId())
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + request.getAuthorId()));

        BlogPost post = new BlogPost(request.getTitle(), request.getPost(), author);
        BlogPost saved = blogPostRepository.save(post);

        return mapToResponse(saved);
    }

    public PostResponse updatePost(Long id, PostRequest request) {
        BlogPost existing = blogPostRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + id));

        Author author = authorRepository.findById(request.getAuthorId())
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + request.getAuthorId()));

        existing.setTitle(request.getTitle());
        existing.setPost(request.getPost());
        existing.setAuthor(author);

        BlogPost updated = blogPostRepository.save(existing);
        return mapToResponse(updated);
    }

    public void deletePost(Long id) {
        BlogPost post = blogPostRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + id));
        blogPostRepository.delete(post);
    }

    private PostResponse mapToResponse(BlogPost post) {
        return new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getPost(),
                post.getAuthor().getId(),
                post.getAuthor().getName()
        );
    }
}