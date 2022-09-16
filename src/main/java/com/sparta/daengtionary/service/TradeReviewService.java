package com.sparta.daengtionary.service;

import com.sparta.daengtionary.configration.error.CustomException;
import com.sparta.daengtionary.configration.error.ErrorCode;
import com.sparta.daengtionary.domain.Member;
import com.sparta.daengtionary.domain.map.Map;
import com.sparta.daengtionary.domain.map.MapReview;
import com.sparta.daengtionary.domain.trade.Trade;
import com.sparta.daengtionary.domain.trade.TradeReview;
import com.sparta.daengtionary.dto.request.ReviewRequestDto;
import com.sparta.daengtionary.dto.response.ResponseBodyDto;
import com.sparta.daengtionary.dto.response.ReviewResponseDto;
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

    @Transactional
    public ResponseEntity<?> createTradeReview(Long tradeNo, ReviewRequestDto requestDto) {
        Member member = tokenProvider.getMemberFromAuthentication();
        Trade trade = tradeService.validateTrade(tradeNo);

        TradeReview tradeReview = TradeReview.builder()
                .member(member)
                .trade(trade)
                .content(requestDto.getContent())
                .build();

        tradeReviewRepository.save(tradeReview);

        return responseBodyDto.success(ReviewResponseDto.builder()
                .reviewNo(tradeReview.getTradeReviewNo())
                .nick(tradeReview.getMember().getNick())
                .content(tradeReview.getContent())
                .build(), "리뷰 생성 완료");
    }

    @Transactional
    public ResponseEntity<?> updateTradeReview(Long tradeNo,Long tradeReviewNo, ReviewRequestDto requestDto ){
        Member member = tokenProvider.getMemberFromAuthentication();
        tradeService.validateTrade(tradeNo);
        TradeReview tradeReview = validateTradeReview(tradeReviewNo,member);

        tradeReview.tradeReviewUpdate(requestDto);

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
