package project.tashboard.api.member;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LoginRequest {
    @NotEmpty
    private String loginId; // 로그인 ID
    @NotEmpty
    private String password; // 비밀번호
}
