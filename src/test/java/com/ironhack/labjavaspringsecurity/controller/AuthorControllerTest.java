package com.ironhack.labjavaspringsecurity.controller;

import com.ironhack.labjavaspringsecurity.model.Author;
import com.ironhack.labjavaspringsecurity.repository.AuthorRepository;
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
class AuthorControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    AuthorRepository authorRepository;

    Author existing;

    @BeforeEach
    void setUp() {
        authorRepository.deleteAll();
        existing = new Author();
        existing.setName("Aiko Tanaka");
        existing = authorRepository.save(existing);
    }

    @Test
    void get_author_by_id_is_public() throws Exception {
        mockMvc.perform(get("/authors/{id}", existing.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(existing.getId()))
                .andExpect(jsonPath("$.name").value("Aiko Tanaka"));
    }

    @Test
    void create_author_requires_admin() throws Exception {
        mockMvc.perform(post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"New Author\"}"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void create_author_admin_ok() throws Exception {
        mockMvc.perform(post("/authors")
                        .with(httpBasic("admin", "password"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"New Author\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("New Author"));
    }

    @Test
    void update_author_requires_admin() throws Exception {
        mockMvc.perform(put("/authors/{id}", existing.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Updated\"}"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void update_author_admin_ok() throws Exception {
        mockMvc.perform(put("/authors/{id}", existing.getId())
                        .with(httpBasic("admin", "password"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Updated\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(existing.getId()))
                .andExpect(jsonPath("$.name").value("Updated"));
    }

    @Test
    void delete_author_requires_admin() throws Exception {
        mockMvc.perform(delete("/authors/{id}", existing.getId()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void delete_author_admin_ok() throws Exception {
        mockMvc.perform(delete("/authors/{id}", existing.getId())
                        .with(httpBasic("admin", "password")))
                .andExpect(status().isNoContent());
        assertThat(authorRepository.existsById(existing.getId())).isFalse();
    }
}

