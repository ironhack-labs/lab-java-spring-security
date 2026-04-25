package com.example.demo;

import com.example.demo.entities.Post;
import com.example.demo.repositories.PostRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PostControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @Test
    public void getAllPosts_PublicAccess_Returns200() throws Exception {
        mockMvc.perform(get("/posts"))
                .andExpect(status().isOk());
    }
    @Test
    public void addPost_Anonymous_ReturnsUnauthorized() throws Exception {
        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isUnauthorized());
    }
    @Test
    public void updatePost_Admin_ReturnsOk() throws Exception {
        Post existingPost = new Post();
        existingPost.setTitle("Old Title");
        existingPost.setPost("Old content");

        Post savedPost = postRepository.save(existingPost);

        Post updatedPost = new Post();
        updatedPost.setTitle("Updated Title");
        updatedPost.setPost("Updated content");

        mockMvc.perform(put("/posts/" + savedPost.getId())
                        .with(user("revan").roles("ADMIN"))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedPost)))
                .andExpect(status().isOk());
    }
}
