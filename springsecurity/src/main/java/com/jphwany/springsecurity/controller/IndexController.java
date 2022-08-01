package com.jphwany.springsecurity.controller;

import com.jphwany.springsecurity.auth.PrincipalDetails;
import com.jphwany.springsecurity.model.Member;
import com.jphwany.springsecurity.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class IndexController {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    MemberRepository memberRepository;


    @GetMapping("/")
    // 로그인이 정상적으로 되었는지 확인하기 위해 매번 /user로 권한 있는 경우에 접속했었는데 (번거로움)
    // 인덱스 페이지 "/" 에서 로그인 되었는지 확인하기
    public String index(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {

        try {
            if(principalDetails.getUsername() != null){
                model.addAttribute("username", principalDetails.getUsername());
            }
        } catch(NullPointerException e){}
        return "index";
    }


    // 일반 회원가입, 로그인 그리고 로그아웃 후 구글 로그인 OAuth2
    @GetMapping("/user")
    public @ResponseBody String user(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        System.out.println(principalDetails.getMember());
        return "user";
    }

    @GetMapping("/admin")
    public @ResponseBody String admin() {
        return "admin";
    }

    @GetMapping("/manager")
    public @ResponseBody String manager() {
        return "manager";
    }

    @GetMapping("/login")
    public String login() {
        return "loginForm";
    }

    @GetMapping("/join")
    public String joinForm() {
        return "joinForm";
    }

    @GetMapping("/loginTest")
    public @ResponseBody String loginTest(Authentication authentication){
        System.out.println("============/loginTest===========");
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal(); // 객체 의존성 주입, 다운 캐스팅, 정보 가져오기
        System.out.println("authentication : " + principalDetails.getMember());
        return "세션 정보 확인";
    }

    @GetMapping("/loginTest2") // 애너테이션으로 작동하게 할 수 있음
    public @ResponseBody String loginTest2(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        System.out.println("============/loginTest2===========");
        System.out.println("userDetails : " + principalDetails.getMember());
        return "세션 정보 확인2";
    }

    @GetMapping("/loginTest3") // OAuth2 로그인 정보 가져오기
    public @ResponseBody String loginOAuthTest(
            Authentication authentication, @AuthenticationPrincipal OAuth2User oauth) {
        System.out.println("============/loginOAuthTest===========");
        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
        System.out.println("authenticaion : " + oauth2User.getAttributes());
        System.out.println("oauth2User : " + oauth.getAttributes());
        return "세션 정보 확인3";
    }

    @PostMapping("/join")
    public String join(Member member) {

        member.setRole("ROLE_USER");
        String rawPassword = member.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        member.setPassword(encPassword);

        memberRepository.save(member);

        return "redirect:/login";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/info")
    public @ResponseBody String info() {
        return "info";
    }
    // SecurityConfig에 .antMatchers("/info/**").access("hasRole('ROLE_ADMIN')") 코드 추가하는 것과 같은 동작


    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/data")
    public @ResponseBody String data() {
        return "data";
    }
    // SecurityConfig에 .antMatchers("/data/**").access("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')") 코드 추가하는 것과 같은 동작

}
/*
   @Secured -> 1개 권한 주고 싶을 때 사용
   @PreAuthorize: 1개 이상 권한 주고 싶을 때 사용
   -> #을 사용하면 파라미터에 접근 가능
   ex) @PreAuthorize("isAuthenticated() and (( #user.name == principal.name ) or hasRole('ROLE_ADMIN'))")

   @PostAuthorize: 메소드가 실행되고 응답하기 직전에 권한을 검사함
   -> 클라이언트에 응답하기 전에 로그인 상태 or 반환되는 사용자 이름, 현재 사용자 이름에 대한 검사
   현 사용자가 관리자 권한을 가지고 있는지 등 권한 후처리를 할 수 있다

*/
