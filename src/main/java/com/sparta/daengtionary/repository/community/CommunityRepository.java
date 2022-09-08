package com.sparta.daengtionary.repository.community;

import com.sparta.daengtionary.domain.Member;
import com.sparta.daengtionary.domain.community.Community;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommunityRepository extends JpaRepository<Community,Long> {
    List<Community> findByMember(Member member);
}