package com.example.demo.service;

import com.example.demo.entities.Post;
import com.example.demo.repositories.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post addPost(Post post) {
        return postRepository.save(post);
    }

    public Post updatePost(Long id, Post post) {
        Post kohnePost = postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post tapılmadı!"));
        kohnePost.setPost(post.getPost());
        kohnePost.setTitle(post.getTitle());
        return postRepository.save(kohnePost);
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}
