package project.tashboard;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.List;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "redirect:/boards";
    }
    @GetMapping("/board")
    public String board() {
        return "board";
    }

    @GetMapping("/layout")
    public String layout() {
        return "layout";
    }

    @GetMapping("/hello")
    public List<String> getHello() {
        return Arrays.asList("안녕하세요", "Hello","asdfasdf");
    }
}
