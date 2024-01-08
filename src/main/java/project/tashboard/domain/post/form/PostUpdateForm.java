package project.tashboard.domain.post.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.tashboard.domain.board.BoardType;
import project.tashboard.domain.member.Member;
import project.tashboard.domain.post.Post;

@Data
@NoArgsConstructor
public class PostUpdateForm {

    @NotNull
    private Long postId;

    @NotBlank
    private String title;

    @NotBlank
    private String contents;

    @NotNull
    private BoardType boardType; // 게시판 종류

    @NotNull
    private Long memberId; // 작성자 ID

    @NotNull
    private String writerName; // 작성자 이름

    public PostUpdateForm(Post post){
        this.postId = post.getPostId();
        this.title = post.getTitle();
        this.contents = post.getContents();
        this.boardType = post.getBoardType();
        this.memberId = post.getMember().getMemberId();
        this.writerName = post.getMember().getName();
    }
}
