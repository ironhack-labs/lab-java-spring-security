# Blog CMS API Documentation

Base URL: `http://localhost:8080`

Authentication for admin routes uses HTTP Basic:
- Username: `admin`
- Password: `admin123`

## Security Rules

- Public: all `GET` routes
- Admin only: `POST`, `PUT`, `DELETE` routes

## Authors

- `GET /api/authors` - list all authors (public)
- `GET /api/authors/{id}` - get one author by id (public)
- `POST /api/authors` - create author (admin)
  - Body:
    ```json
    {
      "name": "Aiko Tanaka"
    }
    ```
- `PUT /api/authors/{id}` - update author (admin)
  - Body:
    ```json
    {
      "name": "Updated Name"
    }
    ```
- `DELETE /api/authors/{id}` - delete author (admin)

## Blog Posts

- `GET /api/posts` - list all posts (public)
- `GET /api/posts/{id}` - get one post by id (public)
- `POST /api/posts` - create post (admin)
  - Body:
    ```json
    {
      "title": "Boost Your Productivity with 10 Easy Tips",
      "post": "Productivity - we all want it...",
      "authorId": 1
    }
    ```
- `PUT /api/posts/{id}` - update post (admin)
  - Body:
    ```json
    {
      "title": "How to Focus",
      "post": "Do you ever sit down to work...",
      "authorId": 1
    }
    ```
- `DELETE /api/posts/{id}` - delete post (admin)

## Error Responses

- `404 Not Found`
  ```json
  {
    "error": "Author not found"
  }
  ```
- `400 Bad Request` (validation)
  ```json
  {
    "name": "Name is required"
  }
  ```
