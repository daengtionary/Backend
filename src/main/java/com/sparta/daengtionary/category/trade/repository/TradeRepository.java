package com.sparta.daengtionary.category.trade.repository;

import com.sparta.daengtionary.category.member.domain.Member;
import com.sparta.daengtionary.category.trade.domain.Trade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TradeRepository extends JpaRepository<Trade, Long> {
    List<Trade> findByMember(Member member);
}