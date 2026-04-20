package org.example.lab.mapper;

import org.example.lab.dto.request.AuthorRequest;
import org.example.lab.dto.response.AuthorResponse;
import org.example.lab.model.Author;
import org.example.lab.repository.AuthorRepository;

public class AuthorMapper
{

    public static Author toAuthor(AuthorRequest request){
        Author author = new Author();
        author.setName(request.getName());
        return author;
    }

    public static AuthorResponse toResponse(Author author){
        return new AuthorResponse(author.getId(), author.getName());
    }

    public static void updateEntity(Author author,AuthorRequest request){
        author.setName(request.getName());
}
}
