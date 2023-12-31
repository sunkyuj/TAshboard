package project.tashboard.domain.board;

import jakarta.persistence.*;
import lombok.Data;
import project.tashboard.domain.post.Post;

import java.util.List;

@Entity
@Data
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long boardId;
    private String name;
    private String description;

    @OneToMany(mappedBy = "board")
    private List<Post> posts;
}
