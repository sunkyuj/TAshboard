package project.tashboard.web.member;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.tashboard.domain.member.Member;
import project.tashboard.domain.member.form.MemberUpdateForm;
import project.tashboard.domain.post.Post;

import java.util.List;
import java.util.Optional;

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

    public Member findByName(String name) {
        return memberRepository.findByName(name);
    }

    public Member updatePostWithForm(MemberUpdateForm form) {

        Member findMember = memberRepository.findByLoginId(form.getLoginId());
        // update post
        findMember.setName(form.getName());
        findMember.setPassword(form.getPassword());

        log.info("findMember: {}", findMember);
        memberRepository.save(findMember); // anti-pattern, but it's okay for now. TODO?
        return findMember;
    }
}
