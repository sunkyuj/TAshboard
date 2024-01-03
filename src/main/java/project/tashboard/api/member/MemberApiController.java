package project.tashboard.api.member;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project.tashboard.domain.member.Member;
import project.tashboard.web.member.MemberService;
import project.tashboard.web.member.SessionConst;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class MemberApiController {

    private final MemberService memberService;

    @GetMapping("/members")
    @ResponseBody
    public List<MemberResponse> getMembers() {
        return memberService.findAll().stream().map(MemberResponse::build).toList();
    }

    @PostMapping("/register")
    public void register(@Validated @RequestBody RegisterRequest registerRequest, BindingResult bindingResult, HttpServletResponse response) throws IOException {
        if (bindingResult.hasErrors()) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            response.sendRedirect("/register");
        }
        memberService.register(registerRequest.toEntity());
        response.sendRedirect("/");
    }

    @PostMapping("/login")
    public void login(@Validated @RequestBody LoginRequest loginRequest, BindingResult bindingResult,
                         @RequestParam(defaultValue = "/") String redirectURL,
                         HttpServletRequest request,
                         HttpServletResponse response) throws IOException {
        if (bindingResult.hasErrors()) {
            response.sendRedirect(request.getContextPath());
        }

        log.info("loginId: {}, password: {}", loginRequest.getLoginId(), loginRequest.getPassword());

        Member loginMember = memberService.login(loginRequest.getLoginId(), loginRequest.getPassword());

        if (loginMember == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            response.sendRedirect(request.getContextPath());

        }

        // 로그인 성공 처리
        HttpSession session = request.getSession(); // 세션이 있으면 있는 세션 반환, 없으면 신규 세션을 생성
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember); // 세션에 로그인 회원 정보 보관

        response.sendRedirect(redirectURL);
    }
    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false); // 세션을 조회해서 있으면 반환, 없으면 null 반환
        if (session != null) {
            session.invalidate(); // 세션 만료
        }
        response.sendRedirect("/");
    }

}
