package com.SpringMVC.Practice.member;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;  // ver2 (1) produces 삭제
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;



//@RequestMapping(value = "/v1/members", produces = {MediaType.APPLICATION_JSON_VALUE}) // ver2 (1) produces 삭제

@RestController
@RequestMapping(value = "/v1/members")

public class MemberController {

    // postMember() 메소드는 회원 정보를 등록해주는 핸들러 메소드
    // @PostMapping은 클라이언트의 요청 데이터(request body)를 서버에 생성할 때 사용하는 애너테이션
    // HTTP 메소드 타입을 동일하게 맞춰줘야한다
    @PostMapping
    public ResponseEntity postMember(@RequestParam("email") String email,
                                     @RequestParam("name") String name,
                                     @RequestParam("phone") String phone){

        System.out.println("# email: " + email);
        System.out.println("# name: " + name);
        System.out.println("# phone: " + phone);

        //ver2 (2) JSON 문자열 입력을 Map 객체로 변경
        // 내부적으로는 Map 객체는 JSON 형식으로 자동 변환 해준다
        Map<String, String> map = new HashMap<>();
        map.put("email", email);
        map.put("name", name);
        map.put("phone", phone);

        // ver2 (3) 리턴 값 ResponseEntity 객체로 변경
        return new ResponseEntity<>(map, HttpStatus.CREATED);
        // HttpStatus.CREATED 클라이언트 POST 요청을 처리, 요청 데이터가 정상 생성되었음을 의미하는 HTTP 응답상태
        // 단순 Map 객체를 리턴해도 괜찮았지만 HTTP 응답 상태를 전달해준 모습, 이러면 클라이언트 요청을
        // 서버가 어떻게 처리했는지 쉽게 알 수 있다
        // 클라이언트 쪽에서 이 HTTP 응답 상태를 기반으로 정상적으로 다음 처리를 할지, 에러 처리를 할지 결정하면 된다
    }

    // ver2 (4) 리턴 값 ResponseEntity 객체로 변경
    @GetMapping("/{member-id}")
    public ResponseEntity getMember(@PathVariable("member-id") long memberId){
        System.out.println("# memberId: " + memberId);

        return new ResponseEntity<>(HttpStatus.OK);
    }


    // ver2 (5) 리턴 값 ResponseEntity 객체로 변경
    @GetMapping
    public ResponseEntity getMembers(){
        System.out.println("# get Members");

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
