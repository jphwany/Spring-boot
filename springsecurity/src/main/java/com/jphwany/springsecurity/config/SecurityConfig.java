package com.jphwany.springsecurity.config;


import com.jphwany.springsecurity.config.oauth.PrincipalOauth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
// Secured 애너테이션 활성화, SecurityConfig 접근 권한 설정이 아니라 Controller 에서 애너테이션으로 관리할 수 있게 된다
public class SecurityConfig {

    @Autowired
    private PrincipalOauth2UserService principalOauth2UserService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.csrf().disable(); // form 태그로만 요청 가능, postman 같은걸로 요청 x, 그렇기에 csrf를 disable.
        http.headers().frameOptions().disable(); // h2 연결시 필요

        http.authorizeRequests()
                // 페이지 권한
                .antMatchers("/user/**").authenticated()
                .antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll()
                //  로그인
                .and()
                .formLogin()
                .loginPage("/login") // 로그인 페이지 경로 설정
                .loginProcessingUrl("/login") // post로 로그인 정보 보낼 때 경로
                .defaultSuccessUrl("/") // 로그인 성공할 때 경로
                .permitAll()
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // 로그아웃 호출할 때 경로
                .logoutSuccessUrl("/login") // 로그아웃 성공했을 때 해당 경로
                .invalidateHttpSession(true) // 로그아웃할 때 인증정보 지우고 세션 무효화
                .and()
                .oauth2Login()
                .loginPage("/login")
                .userInfoEndpoint()
                .userService(principalOauth2UserService);
        return http.build();
    }
}
