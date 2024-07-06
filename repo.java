import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}

public interface PostRepository extends JpaRepository<Post, Long> {
}
