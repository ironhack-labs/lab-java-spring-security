package org.example.lab.dto.response;

public class PostResponse {
    private Long id;
    private String title;
    private String post;
    private Long authorId;
    private String authorName;

    public PostResponse() {
    }

    public PostResponse(Long id, String title, String post, Long authorId, String authorName) {
        this.id = id;
        this.title = title;
        this.post = post;
        this.authorId = authorId;
        this.authorName = authorName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
}
