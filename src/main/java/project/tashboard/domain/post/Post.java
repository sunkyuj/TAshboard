package project.tashboard.domain.post;

import jakarta.persistence.*;
import lombok.Data;
import project.tashboard.domain.comment.Comment;
import project.tashboard.domain.member.Member;

import java.util.List;

@Entity
@Data
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long postId;
    private String title;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")  // FK, 연관관계의 주인
    private Member member;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments;

}
