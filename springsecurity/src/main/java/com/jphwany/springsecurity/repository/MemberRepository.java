package com.jphwany.springsecurity.repository;

import com.jphwany.springsecurity.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,Long> {
    public Member findByUsername(String username);
}
