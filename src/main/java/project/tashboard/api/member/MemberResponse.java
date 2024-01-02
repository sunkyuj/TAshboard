package project.tashboard.api.member;

import lombok.Builder;
import lombok.Data;
import project.tashboard.api.post.PostResponse;
import project.tashboard.domain.comment.Comment;
import project.tashboard.domain.member.Member;
import project.tashboard.domain.post.Post;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class MemberResponse {
    private Long memberId;
    private String loginId;
    private String name;
    private List<PostResponse> posts;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static MemberResponse build(Member member) {
        return MemberResponse.builder()
                .memberId(member.getMemberId())
                .loginId(member.getLoginId())
                .name(member.getName())
                .posts(member.getPosts().stream().map(PostResponse::build).toList())
                .createdAt(member.getCreatedAt())
                .updatedAt(member.getUpdatedAt())
                .build();
    }
}
