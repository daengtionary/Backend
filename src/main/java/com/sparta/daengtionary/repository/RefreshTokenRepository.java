package com.sparta.daengtionary.repository;


import com.sparta.daengtionary.domain.Member;
import com.sparta.daengtionary.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
  Optional<RefreshToken> findByMember(Member member);
}