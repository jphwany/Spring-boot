package com.jphwany.jwt.controller;

import com.jphwany.jwt.model.Member;
import com.jphwany.jwt.respository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class RestApiController {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/home")
    public String home(){
        return "<h1>home</h1>";
    }

    @GetMapping("/api/v1/user")
    public String user() {
        return "user";
    }

    @GetMapping("/api/v1/admin")
    public String admin() {
        return "admin";
    }

    @PostMapping("/token")
    public String token(){
        return "<h1>token</h1>";
    }

    @PostMapping("/join")
    public String join(@RequestBody Member member){
        member.setPassword(bCryptPasswordEncoder.encode(member.getPassword()));
        member.setRoles("ROLE_USER");
        memberRepository.save(member);
        return "회원 가입 완료";
    }
}