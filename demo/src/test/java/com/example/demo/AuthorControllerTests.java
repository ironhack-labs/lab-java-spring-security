package com.example.demo;

import com.example.demo.entities.Author;
import com.example.demo.repositories.AuthorRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class AuthorControllerTests {
    @Autowired
    public MockMvc mockMvc;
    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getAllAuthors_PublicAccess_Returns200() throws Exception {
        mockMvc.perform(get("/authors"))
                .andExpect(status().isOk());
    }

    @Test
    public void addAuthor_Anonymous_ReturnsUnauthorized() throws Exception {
        Author author = new Author();
        author.setName("New Author");

        mockMvc.perform(post("/authors")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(author)))
                .andExpect(status().isUnauthorized());
    }
    @Test
    public void updateAuthor_Admin_ReturnsOk() throws Exception {
        Author existingAuthor = new Author();
        existingAuthor.setName("Old Name");
        Author updatedAuthor = new Author();
        updatedAuthor.setName("Updated Name");

        mockMvc.perform(put("/authors/{id}",2)
                        .with(user("revan").roles("ADMIN"))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedAuthor)))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteAuthor_Admin_ReturnsNoContent() throws Exception {
        Author authorToDelete = new Author();
        authorToDelete.setName("To Be Deleted");

        mockMvc.perform(delete("/authors/{id}",1)
                        .with(user("revan").roles("ADMIN"))
                        .with(csrf()))
                .andExpect(status().isNoContent());
    }
    @Test
    public void addAuthor_NormalUser_ReturnsForbidden() throws Exception {
        Author author = new Author();
        author.setName("Should Fail");
        mockMvc.perform(post("/authors")
                        .with(user("student").roles("USER"))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(author)))
                .andExpect(status().isForbidden());
    }
}