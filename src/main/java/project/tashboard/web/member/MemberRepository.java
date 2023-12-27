package project.tashboard.web.member;

import org.springframework.data.jpa.repository.JpaRepository;
import project.tashboard.domain.member.Member;

import java.util.Locale;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByLoginId(String loginId);
}
