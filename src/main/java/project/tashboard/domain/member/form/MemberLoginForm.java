package project.tashboard.domain.member.form;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class MemberLoginForm {
    @NotEmpty
    private String loginId;
    @NotEmpty
    private String password;
}
