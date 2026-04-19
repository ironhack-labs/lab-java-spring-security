package org.example.spring_security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.spring_security.Dto.AuthorRequest;
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
class AuthorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAuthor_shouldBePublic() throws Exception {
        mockMvc.perform(get("/api/authors/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Aiko Tanaka"));
    }

    @Test
    void addAuthor_shouldRequireAdmin() throws Exception {
        AuthorRequest request = new AuthorRequest();
        request.setName("New Author");

        mockMvc.perform(post("/api/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void addAuthor_shouldWorkForAdmin() throws Exception {
        AuthorRequest request = new AuthorRequest();
        request.setName("New Author");

        mockMvc.perform(post("/api/authors")
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("admin", "admin123"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("New Author"));
    }

    @Test
    void updateAuthor_shouldWorkForAdmin() throws Exception {
        AuthorRequest request = new AuthorRequest();
        request.setName("Updated Author");

        mockMvc.perform(put("/api/authors/1")
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("admin", "admin123"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Author"));
    }

    @Test
    void deleteAuthor_shouldWorkForAdmin() throws Exception {
        mockMvc.perform(delete("/api/authors/3")
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("admin", "admin123")))
                .andExpect(status().isNoContent());
    }
}