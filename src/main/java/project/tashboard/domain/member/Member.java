package project.tashboard.domain.member;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import project.tashboard.domain.post.Post;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class) // JPA Auditing
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "posts")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;
    private String loginId; // 로그인 ID
    private String name; // 사용자 이름
    // TODO: 2021-10-07 비밀번호 암호화
    private String password; // 비밀번호

    @OneToMany(mappedBy = "member")
    private List<Post> posts;

    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
