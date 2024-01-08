package project.tashboard.domain.post.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.tashboard.domain.board.BoardType;
import project.tashboard.domain.member.Member;

@Data
@NoArgsConstructor
public class PostAddForm {

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

    public PostAddForm(Member loginMember){
        this.memberId = loginMember.getMemberId();
        this.writerName = loginMember.getName();
    }
}
