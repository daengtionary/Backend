package com.sparta.daengtionary.category.community.repository;

import com.sparta.daengtionary.category.member.domain.Member;
import com.sparta.daengtionary.category.community.domain.Community;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommunityRepository extends JpaRepository<Community,Long> {
    List<Community> findByMember(Member member);
}