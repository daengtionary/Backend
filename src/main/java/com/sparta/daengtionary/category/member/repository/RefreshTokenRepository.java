package com.sparta.daengtionary.category.member.repository;


import com.sparta.daengtionary.category.member.domain.Member;
import com.sparta.daengtionary.category.member.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByMember(Member member);
}