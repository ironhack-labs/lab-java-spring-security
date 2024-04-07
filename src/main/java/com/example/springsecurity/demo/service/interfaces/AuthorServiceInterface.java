package com.example.springsecurity.demo.service.interfaces;

import com.example.springsecurity.demo.model.Author;
import com.example.springsecurity.demo.model.BlogPost;

public interface AuthorServiceInterface {

    Author saveAuthor(Author author);

    Author updateAuthor (Author author);

    Author deleteAuthor (Author author);
}
