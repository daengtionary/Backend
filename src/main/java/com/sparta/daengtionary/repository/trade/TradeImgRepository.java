package com.sparta.daengtionary.repository.trade;

import com.sparta.daengtionary.domain.trade.Trade;
import com.sparta.daengtionary.domain.trade.TradeImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TradeImgRepository extends JpaRepository<TradeImg,Long> {
    List<TradeImg> findAllByTrade(Trade trade);
}
