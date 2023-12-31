package project.tashboard.domain.post.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import project.tashboard.domain.board.BoardType;
import project.tashboard.domain.member.Member;

@Data
public class PostUpdateForm {

    @NotNull
    private Long postId;

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @NotNull
    private BoardType boardType; // 게시판 종류

    @NotNull
    private Member member; // 작성자
}
