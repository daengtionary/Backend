package com.sparta.daengtionary.category.trade.domain;

import com.sparta.daengtionary.aop.exception.CustomException;
import com.sparta.daengtionary.aop.exception.ErrorCode;
import com.sparta.daengtionary.aop.util.Timestamped;
import com.sparta.daengtionary.category.member.domain.Member;
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

    @JoinColumn(name = "tradeNo", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Trade trade;

    @Column(nullable = false)
    private String content;

    @Builder
    public TradeReview(Member member, Trade trade, String content) {
        this.member = member;
        this.trade = trade;
        this.content = content;
    }

    public void tradeReviewUpdate(String content) {
        this.content = content;
    }

    public void validateMember(Member member) {
        if (!this.member.equals(member)) {
            throw new CustomException(ErrorCode.MAP_WRONG_ACCESS);
        }
    }
}