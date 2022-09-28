package com.sparta.daengtionary.category.trade.repository;

import com.sparta.daengtionary.category.trade.domain.Trade;
import com.sparta.daengtionary.category.trade.domain.TradeReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TradeReviewRepository extends JpaRepository<TradeReview, Long> {
    List<TradeReview> findAllByTradeOrderByCreatedAtDesc(Trade trade);

    List<TradeReview> findAllByTrade(Trade trade);

}
