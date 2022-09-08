package com.sparta.daengtionary.repository.trade;

import com.sparta.daengtionary.domain.Member;
import com.sparta.daengtionary.domain.trade.Trade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TradeRepository extends JpaRepository<Trade, Long> {
    List<Trade> findByMember(Member member);
}