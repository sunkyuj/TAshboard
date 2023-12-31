package project.tashboard.web.post;

import lombok.Builder;
import lombok.Data;
import project.tashboard.domain.comment.Comment;
import project.tashboard.domain.post.Post;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class PostResponse {
    private Long postId;
    private String title;
    private String content;
    private Long memberId;
    private String memberName;
    private List<Comment> comments;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static PostResponse build(Post post) {
        return PostResponse.builder()
                .postId(post.getPostId())
                .title(post.getTitle())
                .content(post.getContents())
                .memberId(post.getMember().getMemberId())
                .memberName(post.getMember().getName())
                .comments(post.getComments())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();
    }
}
