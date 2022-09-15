package com.sparta.daengtionary.repository.trade;

import com.sparta.daengtionary.domain.trade.Trade;
import com.sparta.daengtionary.domain.trade.TradeReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TradeReviewRepository extends JpaRepository<TradeReview,Long> {
    List<TradeReview> findAllByTradeOrderByCreatedAtDesc(Trade trade);
    List<TradeReview> findAllByTrade(Trade trade);

}
