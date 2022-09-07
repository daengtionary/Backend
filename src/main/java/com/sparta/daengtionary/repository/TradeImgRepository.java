package com.sparta.daengtionary.repository;

import com.sparta.daengtionary.domain.Trade;
import com.sparta.daengtionary.domain.TradeImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TradeImgRepository extends JpaRepository<TradeImg,Long> {
    List<TradeImg> findAllByTrade(Trade trade);
}
