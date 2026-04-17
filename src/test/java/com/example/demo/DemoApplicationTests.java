package com.example.demo;

import com.example.demo.model.Author;
import com.example.demo.model.BlogPost;
import com.example.demo.repository.AuthorRepository;
import com.example.demo.repository.BlogPostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DemoApplicationTests {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BlogPostRepository blogPostRepository;

    @LocalServerPort
    private int port;

    private final HttpClient httpClient = HttpClient.newHttpClient();

    @BeforeEach
    void cleanDatabase() {
        blogPostRepository.deleteAll();
        authorRepository.deleteAll();
    }

    @Test
    void contextLoads() {
    }

    @Test
    void getRoutesShouldBePublic() throws Exception {
        Author author = authorRepository.save(new Author(null, "Public Author"));
        blogPostRepository.save(new BlogPost(null, "Public Title", "Public body", author));

        HttpResponse<String> authorsResponse = sendRequest("GET", "/api/authors", null, false);
        assertEquals(200, authorsResponse.statusCode());

        HttpResponse<String> postsResponse = sendRequest("GET", "/api/posts", null, false);
        assertEquals(200, postsResponse.statusCode());
    }

    @Test
    void createAuthorShouldRequireAdmin() throws Exception {
        String payload = "{\"name\":\"New Author\"}";

        HttpResponse<String> unauthorized = sendRequest("POST", "/api/authors", payload, false);
        assertEquals(401, unauthorized.statusCode());

        HttpResponse<String> created = sendRequest("POST", "/api/authors", payload, true);
        assertEquals(201, created.statusCode());
        assertTrue(created.body().contains("\"name\":\"New Author\""));
    }

    @Test
    void updateAuthorShouldWorkForAdmin() throws Exception {
        Author author = authorRepository.save(new Author(null, "Before Update"));
        String payload = "{\"name\":\"After Update\"}";

        HttpResponse<String> updated = sendRequest("PUT", "/api/authors/" + author.getId(), payload, true);
        assertEquals(200, updated.statusCode());
        assertTrue(updated.body().contains("\"name\":\"After Update\""));
    }

    @Test
    void deleteAuthorShouldWorkForAdmin() throws Exception {
        Author author = authorRepository.save(new Author(null, "Delete Me"));

        HttpResponse<String> deleted = sendRequest("DELETE", "/api/authors/" + author.getId(), null, true);
        assertEquals(204, deleted.statusCode());
    }

    @Test
    void createPostShouldRequireAdminAndCreateWithAuthor() throws Exception {
        Author author = authorRepository.save(new Author(null, "Post Author"));
        String payload = """
                {
                  "title":"Created Post",
                  "post":"Created body",
                  "authorId":%d
                }
                """.formatted(author.getId());

        HttpResponse<String> unauthorized = sendRequest("POST", "/api/posts", payload, false);
        assertEquals(401, unauthorized.statusCode());

        HttpResponse<String> created = sendRequest("POST", "/api/posts", payload, true);
        assertEquals(201, created.statusCode());
        assertTrue(created.body().contains("\"title\":\"Created Post\""));
    }

    @Test
    void updatePostShouldWorkForAdmin() throws Exception {
        Author originalAuthor = authorRepository.save(new Author(null, "Original"));
        Author updatedAuthor = authorRepository.save(new Author(null, "Updated"));
        BlogPost post = blogPostRepository.save(new BlogPost(null, "Old", "Old body", originalAuthor));

        String payload = """
                {
                  "title":"New",
                  "post":"New body",
                  "authorId":%d
                }
                """.formatted(updatedAuthor.getId());

        HttpResponse<String> updated = sendRequest("PUT", "/api/posts/" + post.getId(), payload, true);
        assertEquals(200, updated.statusCode());
        assertTrue(updated.body().contains("\"title\":\"New\""));
    }

    @Test
    void deletePostShouldWorkForAdmin() throws Exception {
        Author author = authorRepository.save(new Author(null, "Post Author"));
        BlogPost post = blogPostRepository.save(new BlogPost(null, "Delete", "Delete body", author));

        HttpResponse<String> deleted = sendRequest("DELETE", "/api/posts/" + post.getId(), null, true);
        assertEquals(204, deleted.statusCode());
    }

    private HttpResponse<String> sendRequest(String method, String path, String jsonBody, boolean adminAuth) throws Exception {
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(java.net.URI.create("http://localhost:" + port + path));

        if (jsonBody != null) {
            requestBuilder.header("Content-Type", "application/json");
        }
        if (adminAuth) {
            String token = Base64.getEncoder().encodeToString("admin:admin123".getBytes());
            requestBuilder.header("Authorization", "Basic " + token);
        }

        switch (method) {
            case "GET" -> requestBuilder.GET();
            case "POST" -> requestBuilder.POST(HttpRequest.BodyPublishers.ofString(jsonBody));
            case "PUT" -> requestBuilder.PUT(HttpRequest.BodyPublishers.ofString(jsonBody));
            case "DELETE" -> requestBuilder.DELETE();
            default -> throw new IllegalArgumentException("Unsupported method: " + method);
        }

        return httpClient.send(requestBuilder.build(), HttpResponse.BodyHandlers.ofString());
    }

}
