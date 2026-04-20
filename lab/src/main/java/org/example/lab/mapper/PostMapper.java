package org.example.lab.mapper;

import org.example.lab.dto.request.PostRequest;
import org.example.lab.dto.response.PostResponse;
import org.example.lab.model.Post;

public class PostMapper {
    public static Post toPost(PostRequest request){
        Post post = new Post();
        post.setTitle(post.getTitle());
        post.setPost(post.getPost());
        return post;
    }

    public static PostResponse toResponse(Post post){
        return new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getPost(),
                post.getAuthor().getId(),
                post.getAuthor().getName()

        );
    }

    public static void updateEntity(Post post, PostRequest request){
        post.setTitle(request.getTitle());
        post.setPost(request.getPost());
    }

}
