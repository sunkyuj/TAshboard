package project.tashboard.api.post;

import lombok.Builder;
import lombok.Data;
import project.tashboard.domain.member.Member;

@Data
@Builder
public class PostRequest {
    private Long postId;
    private String title;
    private String content;
//    private Member member;
}
