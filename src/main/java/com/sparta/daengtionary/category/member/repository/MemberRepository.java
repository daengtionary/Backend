package com.sparta.daengtionary.category.member.repository;

import com.sparta.daengtionary.category.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
    Optional<Member> findByKakaoId(Long kakaoId);
    Optional<Member> findByNick(String nick);
    boolean existsByEmail(String email);
    boolean existsByNick(String nick);
}