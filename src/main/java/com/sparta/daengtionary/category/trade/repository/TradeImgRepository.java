package com.sparta.daengtionary.category.trade.repository;

import com.sparta.daengtionary.category.trade.domain.Trade;
import com.sparta.daengtionary.category.trade.domain.TradeImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TradeImgRepository extends JpaRepository<TradeImg, Long> {
    List<TradeImg> findAllByTrade(Trade trade);
}
