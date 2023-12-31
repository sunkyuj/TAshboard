package project.tashboard.web.post;

import org.springframework.data.jpa.repository.JpaRepository;
import project.tashboard.domain.board.Board;
import project.tashboard.domain.post.Post;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {


    List<Post> findByBoard(Board board);


}
