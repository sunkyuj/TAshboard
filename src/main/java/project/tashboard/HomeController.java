package project.tashboard;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import project.tashboard.domain.member.Member;
import project.tashboard.web.member.SessionConst;

import static project.tashboard.domain.board.BoardLists.boardList;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
@Slf4j
public class HomeController {

    public String home(Model model) {
        model.addAttribute("boards", boardList);
        return "home";
    }

    @GetMapping
    public String homeLogin(
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember, Model model) {
        // SessionAttribute 어노테이션으로 세션 확인 로직 생략 가능

        model.addAttribute("boards", boardList);

        // 세션이 유지되면 로그인으로 이동
        model.addAttribute("member", loginMember);
        return "home";
    }
}
