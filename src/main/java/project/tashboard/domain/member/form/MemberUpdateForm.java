package project.tashboard.domain.member.form;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import project.tashboard.domain.member.Member;

@Data
public class MemberUpdateForm {
    @NotEmpty
    private String loginId; // 로그인 ID
    @NotEmpty
    private String name; // 사용자 이름
    @NotEmpty
    private String password; // 비밀번호


    public Member toMember() {
        return Member.builder()
                .loginId(loginId)
                .name(name)
                .password(password)
                .build();
    }
}
