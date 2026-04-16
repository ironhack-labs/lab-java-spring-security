package com.ironhack.service;

import com.ironhack.dto.request.CreatePostRequest;
import com.ironhack.dto.request.UpdatePostRequest;
import com.ironhack.dto.response.PostResponse;
import com.ironhack.entity.Author;
import com.ironhack.entity.Post;
import com.ironhack.exception.NotFoundException;
import com.ironhack.mapper.PostMapper;
import com.ironhack.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final AuthorService authorService;
    private final PostMapper postMapper;

    public PostService(PostRepository postRepository, AuthorService authorService, PostMapper postMapper) {
        this.postRepository = postRepository;
        this.authorService = authorService;
        this.postMapper = postMapper;
    }

    public List<PostResponse> getAll() {
        return postRepository.findAll().stream()
                .map(postMapper::toResponse)
                .toList();
    }

    public PostResponse getById(UUID postId) {
        Post post = findOrThrow(postId);
        return postMapper.toResponse(post);
    }

    public PostResponse create(CreatePostRequest request) {
        Author author = authorService.findOrThrow(request.authorId());
        Post savedPost = postRepository.save(postMapper.toEntity(request, author));
        return postMapper.toResponse(savedPost);
    }

    public PostResponse update(UUID postId, UpdatePostRequest request) {
        Post post = findOrThrow(postId);
        Author author = authorService.findOrThrow(request.authorId());

        post.setTitle(request.title());
        post.setPost(request.post());
        post.setAuthor(author);

        Post updatedPost = postRepository.save(post);
        return postMapper.toResponse(updatedPost);
    }

    public void delete(UUID postId) {
        Post post = findOrThrow(postId);
        postRepository.delete(post);
    }

    private Post findOrThrow(UUID postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("Post not found: " + postId));
    }
}
