package project.tashboard;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static project.tashboard.domain.board.BoardLists.boardList;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
@Slf4j
public class HomeController {

    @GetMapping
    public String home(Model model) {
        model.addAttribute("boards", boardList);
        return "boards/boards";
    }
}
