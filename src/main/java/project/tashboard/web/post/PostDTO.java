package project.tashboard.web.post;

import lombok.Builder;
import lombok.Data;
import project.tashboard.domain.comment.Comment;
import project.tashboard.domain.member.Member;

import java.util.List;

@Data
@Builder
public class PostDTO {
    private Long postId;
    private String title;
    private String content;
    private Member member;
    private List<Comment> comments;
}
