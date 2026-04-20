package org.example.lab.service;


import org.example.lab.dto.request.PostRequest;
import org.example.lab.dto.response.PostResponse;
import org.example.lab.mapper.PostMapper;
import org.example.lab.model.Author;
import org.example.lab.model.Post;
import org.example.lab.repository.AuthorRepository;
import org.example.lab.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {

    private final AuthorRepository authorRepository;
    private final PostRepository postRepository;
    private Post post;


    public PostService(AuthorRepository authorRepository, PostRepository postRepository) {
        this.authorRepository = authorRepository;
        this.postRepository = postRepository;
    }

    public PostResponse createPost(PostRequest request){
        Author author = authorRepository.findById(request.getAuthorId()).orElseThrow(()-> new RuntimeException("Author not found"));
        Post post = PostMapper.toPost(request);
        post.setAuthor(author);
        Post savedPost = postRepository.save(post);
        return PostMapper.toResponse(savedPost);
    }

    public List<PostResponse> getAll() {
        List<Post> posts = postRepository.findAll();
        List<PostResponse> responseList = new ArrayList<>();
        for (Post post : posts) {
            PostResponse response = PostMapper.toResponse(post);
            responseList.add(response);
        }
        return responseList;
    }

    public PostResponse getPostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        return PostMapper.toResponse(post);
    }

    public void delete(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));

        postRepository.delete(post);
    }

    public PostResponse updatePost(Long id, PostRequest request) {
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));
        Author author = authorRepository.findById(id).orElseThrow(() -> new RuntimeException("Author not found"));
        post.setPost(request.getPost());
        post.setTitle(request.getTitle());
        post.setAuthor(author);
        Post updatedPost = postRepository.save(post);
        return PostMapper.toResponse(updatedPost);
    }

}
