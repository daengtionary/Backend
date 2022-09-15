package com.sparta.daengtionary.repository;

import com.sparta.daengtionary.domain.Member;
import com.sparta.daengtionary.domain.Wish;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishRepository extends JpaRepository<Wish,Long> {

    Wish findByMapAndMember(Object map, Member member);
    Wish  findByCommunityAndMember(Object com,Member member);
    Wish findByTradeAndMember(Object trade,Member member);

}
