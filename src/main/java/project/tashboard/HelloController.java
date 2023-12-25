package project.tashboard;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class HelloController {
    @GetMapping("/hello")
    public List<String> getHello() {
        return Arrays.asList("안녕하세요", "Hello");
    }
}
