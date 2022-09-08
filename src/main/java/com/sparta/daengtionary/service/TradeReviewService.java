package com.sparta.daengtionary.service;

import com.sparta.daengtionary.domain.Member;
import com.sparta.daengtionary.domain.trade.Trade;
import com.sparta.daengtionary.dto.request.ReviewRequestDto;
import com.sparta.daengtionary.dto.response.ResponseBodyDto;
import com.sparta.daengtionary.jwt.TokenProvider;
import com.sparta.daengtionary.repository.trade.TradeReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class TradeReviewService {
    private final AwsS3UploadService s3UploadService;
    private TradeReviewRepository tradeReviewRepository;
    private final ResponseBodyDto responseBodyDto;
    private final TradeService tradeService;
    private final TokenProvider tokenProvider;
    private final String imgPath = "/map/review";

//    @Transactional
//    public ResponseEntity<?> createTradeReview(Long tradeNo, ReviewRequestDto requestDto, MultipartFile multipartFile){
//        Member member = tokenProvider.getMemberFromAuthentication();
//        Trade trade = tradeService.validateTrade(tradeNo);
//
//    }

//
//    private void

}
