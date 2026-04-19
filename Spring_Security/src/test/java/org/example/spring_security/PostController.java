package org.example.spring_security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.spring_security.Dto.PostRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getPost_shouldBePublic() throws Exception {
        mockMvc.perform(get("/api/posts/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Boost Your Productivity with 10 Easy Tips"))
                .andExpect(jsonPath("$.authorName").value("Aiko Tanaka"));
    }

    @Test
    void addPost_shouldRequireAdmin() throws Exception {
        PostRequest request = new PostRequest();
        request.setAuthorId(1L);
        request.setTitle("New Post");
        request.setPost("Some content");

        mockMvc.perform(post("/api/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void addPost_shouldWorkForAdmin() throws Exception {
        PostRequest request = new PostRequest();
        request.setAuthorId(1L);
        request.setTitle("New Post");
        request.setPost("Some content");

        mockMvc.perform(post("/api/posts")
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("admin", "admin123"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("New Post"))
                .andExpect(jsonPath("$.authorId").value(1));
    }

    @Test
    void updatePost_shouldWorkForAdmin() throws Exception {
        PostRequest request = new PostRequest();
        request.setAuthorId(2L);
        request.setTitle("Updated Title");
        request.setPost("Updated content");

        mockMvc.perform(put("/api/posts/1")
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("admin", "admin123"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Title"))
                .andExpect(jsonPath("$.authorId").value(2));
    }

    @Test
    void deletePost_shouldWorkForAdmin() throws Exception {
        mockMvc.perform(delete("/api/posts/2")
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("admin", "admin123")))
                .andExpect(status().isNoContent());
    }
}
