package com.sparta.daengtionary.domain;

import com.sparta.daengtionary.configration.error.CustomException;
import com.sparta.daengtionary.configration.error.ErrorCode;
import com.sparta.daengtionary.dto.request.TradeRequestDto;
import com.sparta.daengtionary.util.Timestamped;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.procedure.spi.ParameterRegistrationImplementor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
public class Trade extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tradeNo;

    @JoinColumn(name = "mumber_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private String status;

    @Column
    private int view;

    @Builder
    public Trade(Long tradeNo, Member member, String title, String content, int price){
        this.tradeNo = tradeNo;
        this.member = member;
        this.title = title;
        this.content = content;
        this.price = price;
        this.status = "판매중";
        this.view = 0;
    }

    public void updateTrade(TradeRequestDto requestDto){
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.price = requestDto.getPrice();
        this.status = requestDto.getStatus();
    }

    public void updateStatus(String status){
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