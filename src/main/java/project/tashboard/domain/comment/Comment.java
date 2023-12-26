package project.tashboard.domain.comment;

import jakarta.persistence.*;
import lombok.Data;
import project.tashboard.domain.post.Post;

@Data
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")  // FK, 연관관계의 주인
    private Post post;
}
