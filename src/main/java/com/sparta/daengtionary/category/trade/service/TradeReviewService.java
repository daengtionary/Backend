package com.sparta.daengtionary.category.trade.service;

import com.sparta.daengtionary.aop.exception.CustomException;
import com.sparta.daengtionary.aop.exception.ErrorCode;
import com.sparta.daengtionary.category.member.domain.Member;
import com.sparta.daengtionary.category.trade.domain.Trade;
import com.sparta.daengtionary.category.trade.domain.TradeReview;
import com.sparta.daengtionary.aop.dto.ResponseBodyDto;
import com.sparta.daengtionary.category.recommend.dto.response.ReviewResponseDto;
import com.sparta.daengtionary.aop.jwt.TokenProvider;
import com.sparta.daengtionary.category.trade.repository.TradeReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TradeReviewService {
    private final TradeReviewRepository tradeReviewRepository;
    private final ResponseBodyDto responseBodyDto;
    private final TradeService tradeService;
    private final TokenProvider tokenProvider;

    @Transactional
    public ResponseEntity<?> createTradeReview(Long tradeNo, String  content) {
        Member member = tokenProvider.getMemberFromAuthentication();
        Trade trade = tradeService.validateTrade(tradeNo);

        TradeReview tradeReview = TradeReview.builder()
                .member(member)
                .trade(trade)
                .content(content)
                .build();

        tradeReviewRepository.save(tradeReview);

        return responseBodyDto.success(ReviewResponseDto.builder()
                .reviewNo(tradeReview.getTradeReviewNo())
                .nick(tradeReview.getMember().getNick())
                .content(tradeReview.getContent())
                .build(), "리뷰 생성 완료");
    }

    @Transactional
    public ResponseEntity<?> updateTradeReview(Long tradeNo,Long tradeReviewNo, String content ){
        Member member = tokenProvider.getMemberFromAuthentication();
        tradeService.validateTrade(tradeNo);
        TradeReview tradeReview = validateTradeReview(tradeReviewNo,member);

        tradeReview.tradeReviewUpdate(content);

        return responseBodyDto.success(ReviewResponseDto.builder()
                        .reviewNo(tradeReview.getTradeReviewNo())
                        .nick(tradeReview.getMember().getNick())
                        .content(tradeReview.getContent())
                .build(),"수정 성공");
    }

    @Transactional
    public ResponseEntity<?> deleteTradeReview(Long tradeNo, Long tradeReviewNo) {
        Member member = tokenProvider.getMemberFromAuthentication();
        tradeService.validateTrade(tradeNo);
        TradeReview tradeReview = validateTradeReview(tradeReviewNo, member);
        tradeReviewRepository.delete(tradeReview);

        return responseBodyDto.success("삭제 성공");
    }



    private TradeReview validateTradeReview(Long tradeReviewNo, Member member) {
        TradeReview tradeReview = tradeReviewRepository.findById(tradeReviewNo).orElseThrow(
                () -> new CustomException(ErrorCode.REVIEW_NOT_FOUND)
        );
        tradeReview.validateMember(member);
        return tradeReview;
    }

}
