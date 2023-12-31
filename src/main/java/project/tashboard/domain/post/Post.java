package project.tashboard.domain.post;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import project.tashboard.domain.board.BoardType;
import project.tashboard.domain.comment.Comment;
import project.tashboard.domain.member.Member;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class) // JPA Auditing
@ToString(exclude = "member")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long postId;
    private String title;
    private String contents;

    @Enumerated(EnumType.STRING) // Enum 값을 DB에 String으로 저장
    private BoardType boardType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")  // FK, 연관관계의 주인
    private Member member;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments;

    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


}
