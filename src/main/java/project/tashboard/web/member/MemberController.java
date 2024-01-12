package project.tashboard.web.member;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.tashboard.api.member.LoginRequest;
import project.tashboard.api.member.MemberResponse;
import project.tashboard.api.member.RegisterRequest;
import project.tashboard.domain.member.Member;
import project.tashboard.domain.member.form.MemberLoginForm;
import project.tashboard.domain.member.form.MemberRegisterForm;
import project.tashboard.domain.member.form.MemberUpdateForm;
import project.tashboard.domain.post.form.PostUpdateForm;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/register")
    public String registerForm(@ModelAttribute("member") MemberRegisterForm form) {
        return "members/registerForm";
    }

    @PostMapping("/register")
    public void register(@Validated @ModelAttribute MemberRegisterForm form, BindingResult bindingResult, HttpServletResponse response) throws IOException {
        if (bindingResult.hasErrors()) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            response.sendRedirect("/register");
        }
        memberService.register(form.toMember());
        response.sendRedirect("/");
    }


    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginForm") MemberLoginForm form) {
        return "members/loginForm";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute MemberLoginForm form, BindingResult bindingResult,
                        @RequestParam(defaultValue = "/") String redirectURL,
                        HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return "members/loginForm";
        }

        Member loginMember = memberService.login(form.getLoginId(), form.getPassword());

        if (loginMember == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "members/loginForm";
        }

        // 로그인 성공 처리
        HttpSession session = request.getSession(); // 세션이 있으면 있는 세션 반환, 없으면 신규 세션을 생성
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember); // 세션에 로그인 회원 정보 보관
        log.info("POST /login loginMember={}", loginMember);
        return "redirect:"+redirectURL;
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false); // 세션을 조회해서 있으면 반환, 없으면 null 반환
        if (session != null) {
            session.invalidate(); // 세션 만료
        }
        return "redirect:/";
    }

    @GetMapping("/mypage")
    public String myPage(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false); // 세션을 조회해서 있으면 반환, 없으면 null 반환
        if (session == null) {
            return "redirect:/login";
        }
        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        model.addAttribute("member", loginMember);
        return "members/myPage";
    }

    @GetMapping("/mypage/edit")
    public String editForm(@ModelAttribute("member") MemberRegisterForm form, HttpServletRequest request) {
        HttpSession session = request.getSession(false); // 세션을 조회해서 있으면 반환, 없으면 null 반환
        if (session == null) {
            return "redirect:/login";
        }
        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        form.setLoginId(loginMember.getLoginId());
        form.setName(loginMember.getName());
        form.setPassword(loginMember.getPassword());
        return "members/editForm";
    }

    @PostMapping("/mypage/edit")
    public String editMyInfo(@Validated @ModelAttribute("member") MemberUpdateForm form,
                             BindingResult bindingResult,
                             Model model,
                             HttpServletRequest request,
                             RedirectAttributes redirectAttributes) {
        HttpSession session = request.getSession(false); // 세션을 조회해서 있으면 반환, 없으면 null 반환
        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        if(member == null) {
            bindingResult.reject("memberNotFound", "작성자가 존재하지 않습니다.");
        }

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "posts/editForm";
        }

        //성공 로직
        Member updatedMember = memberService.updatePostWithForm(form);
        session.setAttribute(SessionConst.LOGIN_MEMBER, updatedMember); // session update

        redirectAttributes.addAttribute("status", true);
        return "redirect:/mypage";
    }

}
