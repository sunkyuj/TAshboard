package project.tashboard.domain.board;

import org.springframework.data.jpa.repository.JpaRepository;
import project.tashboard.domain.post.Post;

public interface BoardRepository extends JpaRepository<Board, Long> {


}
