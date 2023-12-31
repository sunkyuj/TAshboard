package project.tashboard.web.member;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.tashboard.domain.member.Member;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MemberService {
    private final MemberRepository memberRepository;

    public Member login(String loginId, String password) {
        Member member = memberRepository.findByLoginId(loginId);
        log.info("member: {}", member);
        if (member!=null && member.getPassword().equals(password)) {
            return member;
        }
        return null;
    }

    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    public void register(Member member) {
        memberRepository.save(member);
    }

    public Member findByName(String writerName) {
        return memberRepository.findByName(writerName);
    }
}
