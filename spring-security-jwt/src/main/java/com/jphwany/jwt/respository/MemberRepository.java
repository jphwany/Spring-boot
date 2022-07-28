package com.jphwany.jwt.respository;

import com.jphwany.jwt.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    public Member findByUsername(String member);
}   // username 기준으로 검색할 수 있게 findByUsername() 메소드 생성
