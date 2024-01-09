package project.tashboard;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    public String homeLogin(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember, Model model) {
        model.addAttribute("boards", boardList);
        return "home";
    }
}
