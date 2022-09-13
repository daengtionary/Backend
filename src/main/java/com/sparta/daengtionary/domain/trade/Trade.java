package com.sparta.daengtionary.domain.trade;

import com.sparta.daengtionary.configration.error.CustomException;
import com.sparta.daengtionary.configration.error.ErrorCode;
import com.sparta.daengtionary.domain.Member;
import com.sparta.daengtionary.dto.request.TradeRequestDto;
import com.sparta.daengtionary.util.Timestamped;
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
    private String content;

    @Column(nullable = false)
    private String category;
    @Column(nullable = false)
    private int price;
    @Column(nullable = false)
    private String status;
    @Column
    private int view;
    @OneToMany(mappedBy = "trade")
    private List<TradeImg> tradeImgs;


    @Builder
    public Trade(Long tradeNo, Member member, String title, String content, int price,
                 String category) {
        this.tradeNo = tradeNo;
        this.member = member;
        this.title = title;
        this.content = content;
        this.category = category;
        this.price = price;
        this.status = "판매중";
        this.view = 0;
    }

    public void updateTrade(TradeRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.category = requestDto.getCategory();
        this.price = requestDto.getPrice();
        this.status = requestDto.getStatus();
    }

    public void updateStatus(String status) {
        this.status = status;
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