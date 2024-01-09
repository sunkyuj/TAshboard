package project.tashboard.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import project.tashboard.web.member.LoginInterceptor;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new LogInterceptor())
//                .order(1) // 필터와 인터셉터의 순서를 지정할 수 있음
//                .addPathPatterns("/**") // 모든 요청에 인터셉터 적용
//                .excludePathPatterns("/css/**", "/*.ico", "/error"); // 인터셉터 제외 경로

        registry.addInterceptor(new LoginInterceptor())
                .order(1)
                .addPathPatterns("/mypage","/**/add","/**/edit","/**/delete") // 모든 요청에 인터셉터 적용
        ;
//                .excludePathPatterns("/**","/members", "/register", "/login", "/logout", "/css/**", "/*.ico", "/error"); // 인터셉터 제외 경로
    }

}
