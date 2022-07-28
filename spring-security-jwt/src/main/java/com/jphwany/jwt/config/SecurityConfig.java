package com.jphwany.jwt.config;



import com.jphwany.jwt.filter.FirstFilter;
import com.jphwany.jwt.filter.JwtAuthenticationFilter;
import com.jphwany.jwt.filter.JwtAuthorizationFilter;
import com.jphwany.jwt.respository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CorsFilter corsFilter;
    private final MemberRepository memberRepository;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.addFilterBefore(new FirstFilter(), BasicAuthenticationFilter.class);
        // 필터 이름.class 가 실행되기 전에 만든 필터를 먼저 적용

        http.csrf().disable();
        http.headers().frameOptions().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
//                .addFilter(corsFilter)
                .formLogin().disable()
                .httpBasic().disable()
                .apply(new CustomDsl())
                // CustomDsl이라는 내부 클래스를 만들어 .addFilter(new JwtAuthenticationFilter(authenticationManager()))
                // 처리를 통해 해당 필터를 적용
                .and()
                .authorizeRequests()
                .antMatchers("/api/v1/user/**")
                .access("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
                .antMatchers("/api/v1/manager/**")
                .access("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
                .antMatchers("/api/v1/admin/**")
                .access("hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll();
        return http.build();
    }

    public class CustomDsl extends AbstractHttpConfigurer<CustomDsl, HttpSecurity> {

        @Override
        public void configure(HttpSecurity builder) throws Exception {
            AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);
            builder
                    .addFilter(corsFilter)
                    .addFilter(new JwtAuthenticationFilter(authenticationManager))
                    .addFilter(new JwtAuthorizationFilter(authenticationManager, memberRepository));
            // WebSecurityConfigureAdapter 가 deprecated 되면서 내부에 클래스를 만들어주거나 별도의 처리가 필요
        }
    }
}

/*
    JWT를 사용하기 위한 기본 설정

    JWT는 headers에 Authorization 값에 토큰을 보내는 방식 (Bearer)
    토큰 정보는 노출되더라도 그 위험성은 낮다
    => 유효 시간을 지정해줬기 때문

    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    Web은 기본적으로 무상태, stateless인데 session, cookie를 사용할 수 있다
    코드 내용은, session / cookie를 만들지 않고 STATELESS로 진행

    .formLogin().disable()
    form Login 사용 x

    .httpBasis().disable()
    http 통신할 때 headers에 Authorization 값을 ID, Password를 입력하는 방식
    https를 사용하면 ID와 Password가 암호화되어 전달
    http 로그인 방식을 사용하지 않음

 */