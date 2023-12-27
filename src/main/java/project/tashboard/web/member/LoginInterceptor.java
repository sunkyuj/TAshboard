package project.tashboard.web.member;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;



@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI();
        log.info("인증 체크 인터셉터 실행 {}", requestURI);

        HttpSession session = request.getSession(false);
        if(session == null || session.getAttribute(SessionConst.LOGIN_MEMBER)==null){
            log.info("미인증 사용자 요청 {}", requestURI);
            // 로그인으로 redirect
            response.sendRedirect("/login?redirectURL="+requestURI); // redirectURL을 통해 로그인 성공 후 다시 돌아올 수 있도록 함
            return false; // 여기가 중요! 미인증 사용자는 다음 필터를 타지 않도록 해야함
        }

        return true;
    }

}
