package com.jphwany.springsecurity.config.oauth;

import com.jphwany.springsecurity.auth.PrincipalDetails;
import com.jphwany.springsecurity.model.Member;
import com.jphwany.springsecurity.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private MemberRepository memberRepository;

    /*
       loadUser 메소드를 오버라이딩 -> 구글로부터 받은 userRequest 데이터에 대한 후처리 함수
       userRequest에 담긴 정보를 확인할 수 있는 메소드
        userRequest.getClientRegistration()
        userRequest.getAccessToken().getTokenValue()
        super.loadUser(userRequest).getAttributes()
    */
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = super.loadUser(userRequest);

        String provider = userRequest.getClientRegistration().getClientId();
        String providerId = oauth2User.getAttribute("sub");
        String username = oauth2User.getAttribute("name");
        String email = oauth2User.getAttribute("email");
        String role = "ROSE_USER";

        Member memberEntity = memberRepository.findByUsername(username);

        if(memberEntity == null){
            // memberEntity가 null 값일 때는 oauth로 처음 로그인 했을 때라서 회원가입 처리를 해준다
            // null이 아닌 경우에 기존의 한 번이라도 로그인 한 이력이 있기 때문에 별도 처리를 하진 않는다
            memberEntity = Member.builder()
                    .username(username)
                    .email(email)
                    .role(role)
                    .provider(provider)
                    .providerId(providerId)
                    .build();
            memberRepository.save(memberEntity);
        }
        return new PrincipalDetails(memberEntity, oauth2User.getAttributes());
    }
}
