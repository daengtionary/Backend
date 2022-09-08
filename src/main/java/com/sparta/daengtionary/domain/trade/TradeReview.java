package com.sparta.daengtionary.domain.trade;

import com.sparta.daengtionary.configration.error.CustomException;
import com.sparta.daengtionary.configration.error.ErrorCode;
import com.sparta.daengtionary.domain.Member;
import com.sparta.daengtionary.dto.request.ReviewRequestDto;
import com.sparta.daengtionary.util.Timestamped;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class TradeReview extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tradeReviewNo;

    @JoinColumn(name = "memberNo", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @JoinColumn(name = "tradeNo",nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Trade trade;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private int star;

    @Column
    private String imgUrl;

    @Builder
    public TradeReview(Member member,Trade trade,ReviewRequestDto requestDto,String imgUrl){
        this.member = member;
        this.trade = trade;
        this.content = requestDto.getContent();
        this.star = requestDto.getStar();
        this.imgUrl = imgUrl;
    }

    public void TradeReviewUpdate(ReviewRequestDto requestDto, String imgUrl){
        this.content = requestDto.getContent();
        this.star = requestDto.getStar();
        this.imgUrl = imgUrl;
    }

    public void validateMember(Member member){
        if(!this.member.equals(member)){
            throw new CustomException(ErrorCode.MAP_WRONG_ACCESS);
        }
    }



}
