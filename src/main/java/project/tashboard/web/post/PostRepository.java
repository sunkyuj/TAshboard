package project.tashboard.web.post;

import org.springframework.data.jpa.repository.JpaRepository;
import project.tashboard.domain.post.Post;

public interface PostRepository extends JpaRepository<Post, Long> {


}
