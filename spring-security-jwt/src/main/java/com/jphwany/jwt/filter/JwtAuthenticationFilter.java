package com.jphwany.jwt.filter;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jphwany.jwt.model.Member;
import com.jphwany.jwt.oauth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Date;


@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
//        System.out.println("로그인 시도");
//        try {
//            BufferedReader br = request.getReader();
//
//            String input = null;
//            while((input = br.readLine()) != null) {
//                System.out.println(input);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();;
//        }
//        // request 에 담긴 정보를 가져와서 출력하는 코드
//
//        return super.attemptAuthentication(request, response);
//  jwt로 로그인 처리하기 위함

        try {
            ObjectMapper om = new ObjectMapper(); // json 데이터를 파싱하기 위함
            Member member = om.readValue(request.getInputStream(), Member.class); // Member 클래스와 비교

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(member.getUsername(), member.getPassword());

            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            return authentication;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        System.out.println("인증 성공");
        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();

        String jwtToken = JWT.create()
                .withSubject("cos jwt token") // 토큰 이름
                .withExpiresAt(new Date(System.currentTimeMillis() + (60 * 1000 * 10))) // 유효기간 설정 10분
                .withClaim("id", principalDetails.getMember().getId()) // 페이로드에 담긴 정보들을 설정
                .withClaim("username", principalDetails.getMember().getUsername()) // 페이로드에 담긴 정보들을 설정
                .sign(Algorithm.HMAC512("cos_jwt_token")); // 저 값을 가지고 있어야 해석할 수 있음
        response.addHeader("Authorization", "Bearer " + jwtToken); // 헤더에 더해줌
        // "Bearer " 띄어쓰기 한 후 토큰 값 붙여주는 형식
    }
}
/*
   PrincipalDetailsService 의 loadUserByUsername() 메소드가 실행된 후 정상 작동된다면 authentication return
   로그인이 정상적으로 된다면 authentication 객체를 session 에 저장
   attemptAuthentication 메소드가 정상적으로 작동하게 되면 successfulAuthentication 메소드를 실행
   해당 메소드에서 JWT 토큰을 만들어서 요청한 사용자에게 JWT 토큰을 응답으로 돌려주게된다
 */
