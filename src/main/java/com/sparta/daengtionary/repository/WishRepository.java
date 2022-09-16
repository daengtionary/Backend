package com.sparta.daengtionary.repository;

import com.sparta.daengtionary.domain.Member;
import com.sparta.daengtionary.domain.Wish;
import com.sparta.daengtionary.domain.community.Community;
import com.sparta.daengtionary.domain.map.Map;
import com.sparta.daengtionary.domain.trade.Trade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishRepository extends JpaRepository<Wish,Long> {

    List<Wish> findAllByMap(Map map);
    List<Wish> findAllByCommunity(Community community);
    List<Wish> findAllByTrade(Trade trade);

    Wish findByMapAndMember(Object map, Member member);
    Wish  findByCommunityAndMember(Object com,Member member);
    Wish findByTradeAndMember(Object trade,Member member);

}
