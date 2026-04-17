package com.ironhack.labjavaspringsecurity.controller;

import com.ironhack.labjavaspringsecurity.model.Author;
import com.ironhack.labjavaspringsecurity.model.BlogPost;
import com.ironhack.labjavaspringsecurity.repository.AuthorRepository;
import com.ironhack.labjavaspringsecurity.repository.BlogPostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class BlogPostControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    BlogPostRepository blogPostRepository;

    Author author1;
    Author author2;
    BlogPost existing;

    @BeforeEach
    void setUp() {
        blogPostRepository.deleteAll();
        authorRepository.deleteAll();

        author1 = new Author();
        author1.setName("Aiko Tanaka");
        author1 = authorRepository.save(author1);

        author2 = new Author();
        author2.setName("Jonas Schmidt");
        author2 = authorRepository.save(author2);

        existing = new BlogPost();
        existing.setAuthor(author1);
        existing.setTitle("How to Focus");
        existing.setPost("Do you ever sit down to work and find yourself ...");
        existing = blogPostRepository.save(existing);
    }

    @Test
    void get_post_by_id_is_public() throws Exception {
        mockMvc.perform(get("/posts/{id}", existing.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(existing.getId()))
                .andExpect(jsonPath("$.title").value("How to Focus"))
                .andExpect(jsonPath("$.author.id").value(author1.getId()))
                .andExpect(jsonPath("$.author.name").value("Aiko Tanaka"));
    }

    @Test
    void create_post_requires_admin() throws Exception {
        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"authorId\":" + author1.getId() + ",\"title\":\"New\",\"post\":\"Text\"}"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void create_post_admin_ok() throws Exception {
        mockMvc.perform(post("/posts")
                        .with(httpBasic("admin", "password"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"authorId\":" + author1.getId() + ",\"title\":\"New\",\"post\":\"Text\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.title").value("New"))
                .andExpect(jsonPath("$.author.id").value(author1.getId()));
    }

    @Test
    void update_post_requires_admin() throws Exception {
        mockMvc.perform(put("/posts/{id}", existing.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"authorId\":" + author2.getId() + ",\"title\":\"Updated\",\"post\":\"Updated text\"}"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void update_post_admin_ok() throws Exception {
        mockMvc.perform(put("/posts/{id}", existing.getId())
                        .with(httpBasic("admin", "password"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"authorId\":" + author2.getId() + ",\"title\":\"Updated\",\"post\":\"Updated text\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(existing.getId()))
                .andExpect(jsonPath("$.title").value("Updated"))
                .andExpect(jsonPath("$.author.id").value(author2.getId()))
                .andExpect(jsonPath("$.author.name").value("Jonas Schmidt"));
    }

    @Test
    void delete_post_requires_admin() throws Exception {
        mockMvc.perform(delete("/posts/{id}", existing.getId()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void delete_post_admin_ok() throws Exception {
        mockMvc.perform(delete("/posts/{id}", existing.getId())
                        .with(httpBasic("admin", "password")))
                .andExpect(status().isNoContent());
        assertThat(blogPostRepository.existsById(existing.getId())).isFalse();
    }
}

