package com.sparta.daengtionary.repository;

import com.sparta.daengtionary.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
    Optional<Member> findByKakaoId(Long kakaoId);
    boolean existsByEmail(String email);
    boolean existsByNickname(String nickname);
}