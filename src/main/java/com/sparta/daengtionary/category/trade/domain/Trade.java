package com.sparta.daengtionary.category.trade.domain;

import com.sparta.daengtionary.aop.exception.CustomException;
import com.sparta.daengtionary.aop.exception.ErrorCode;
import com.sparta.daengtionary.aop.util.Timestamped;
import com.sparta.daengtionary.category.member.domain.Member;
import com.sparta.daengtionary.category.trade.dto.request.TradeRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Trade extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tradeNo;
    @JoinColumn(name = "memberNo", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private String stuffStatus;
    @Column(nullable = false)
    private String exchange;
    @Column(nullable = false)
    private int price;
    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String postStatus;

    @Column
    private int view;

    @OneToMany(mappedBy = "trade")
    private List<TradeImg> tradeImgs;

    @Builder
    public Trade(Long tradeNo, Member member, String title, String address, String stuffStatus,
                 String exchange, String content, int price, String postStatus) {
        this.tradeNo = tradeNo;
        this.member = member;
        this.title = title;
        this.address = address;
        this.stuffStatus = stuffStatus;
        this.exchange = exchange;
        this.content = content;
        this.price = price;
        this.postStatus = postStatus;
        this.view = 0;
    }

    public void updateTrade(TradeRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.address = requestDto.getAddress();
        this.price = requestDto.getPrice();
        this.exchange = requestDto.getExchange();
        this.postStatus = requestDto.getPostStatus();
        this.stuffStatus = requestDto.getStuffStatus();
    }

    public void updateStatus() {
        this.postStatus = "판매 완료";
    }

    public void viewUpdate() {
        this.view += 1;
    }

    public void validateMember(Member member) {
        if (!this.member.equals(member)) {
            throw new CustomException(ErrorCode.MAP_WRONG_ACCESS);
        }
    }
}