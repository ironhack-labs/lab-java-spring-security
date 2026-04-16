package com.ironhack.mapper;

import com.ironhack.dto.request.CreatePostRequest;
import com.ironhack.dto.response.PostResponse;
import com.ironhack.entity.Author;
import com.ironhack.entity.Post;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {
    private final AuthorMapper authorMapper;

    public PostMapper(AuthorMapper authorMapper) {
        this.authorMapper = authorMapper;
    }

    public Post toEntity(CreatePostRequest request, Author author) {
        return new Post(
                request.title(),
                request.post(),
                author
        );
    }

    public PostResponse toResponse(Post post) {
        return new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getPost(),
                authorMapper.toResponse(post.getAuthor())
        );
    }
}
