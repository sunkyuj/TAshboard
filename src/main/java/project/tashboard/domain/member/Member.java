package project.tashboard.domain.member;

import jakarta.persistence.*;
import lombok.Data;
import project.tashboard.domain.post.Post;

import java.util.List;

@Data
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;
    private String loginId; // 로그인 ID
    private String name; // 사용자 이름
    private String password; // 비밀번호

    @OneToMany(mappedBy = "member")
    private List<Post> posts;


}
