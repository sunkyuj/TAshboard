package project.tashboard;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import project.tashboard.domain.board.BoardType;
import project.tashboard.domain.member.Member;
import project.tashboard.domain.post.Post;
import project.tashboard.web.member.MemberRepository;
import project.tashboard.web.post.PostRepository;

@Component
@Transactional
@RequiredArgsConstructor
public class SampleDataInit {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    // 테스트용 데이터 추가
    @PostConstruct
    public void init() {

        Member member1 = new Member();
        member1.setLoginId("test1");
        member1.setPassword("1234");
        member1.setName("테스트1");
        memberRepository.save(member1);

        Member member2 = new Member();
        member2.setLoginId("test2");
        member2.setPassword("1234");
        member2.setName("테스트2");
        memberRepository.save(member2);

        Post post1 = new Post();
        post1.setTitle("테스트용 제목1");
        post1.setContent("테스트용 내용1");
        post1.setMember(member1);
        post1.setBoardType(BoardType.TA);
        postRepository.save(post1);

        Post post2 = new Post();
        post2.setTitle("테스트용 제목2");
        post2.setContent("테스트용 내용2");
        post2.setMember(member2);
        post2.setBoardType(BoardType.TA);
        postRepository.save(post2);

        Post post3 = new Post();
        post3.setTitle("테스트용 제목3");
        post3.setContent("테스트용 내용3");
        post3.setMember(member1);
        post3.setBoardType(BoardType.SCHOOL);
        postRepository.save(post3);

    }
}
