import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class BlogController {

    @Autowired
    private AuthorService authorService;

    @Autowired
    private PostService postService;

    // GET author by id
    @GetMapping("/author/{id}")
    public Author getAuthorById(@PathVariable Long id) {
        return authorService.getAuthorById(id);
    }

    // POST new author
    @PostMapping("/author")
    public Author addAuthor(@RequestBody Author author) {
        return authorService.addAuthor(author);
    }

    // PUT update author
    @PutMapping("/author")
    public Author updateAuthor(@RequestBody Author author) {
        return authorService.updateAuthor(author);
    }

    // DELETE author by id
    @DeleteMapping("/author/{id}")
    public void deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
    }

    // GET post by id
    @GetMapping("/post/{id}")
    public Post getPostById(@PathVariable Long id) {
        return postService.getPostById(id);
    }

    // POST new post
    @PostMapping("/post")
    public Post addPost(@RequestBody Post post) {
        return postService.addPost(post);
    }

    // PUT update post
    @PutMapping("/post")
    public Post updatePost(@RequestBody Post post) {
        return postService.updatePost(post);
    }

    // DELETE post by id
    @DeleteMapping("/post/{id}")
    public void deletePost(@PathVariable Long id) {
        postService.deletePost(id);
    }
}
