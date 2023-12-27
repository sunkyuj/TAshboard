package project.tashboard;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import project.tashboard.domain.post.Post;
import project.tashboard.web.post.PostRepository;

@Component
@RequiredArgsConstructor
public class SampleDataInit {
    private final PostRepository postRepository;

    // 테스트용 데이터 추가
    @PostConstruct
    public void init() {
        Post post1 = new Post();
        post1.setTitle("테스트용 제목1");
        post1.setContent("테스트용 내용1");
        postRepository.save(post1);

        Post post2 = new Post();
        post2.setTitle("테스트용 제목2");
        post2.setContent("테스트용 내용2");
        postRepository.save(post2);
    }
}
