package com.sparta.daengtionary.category.trade.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class TradeResponseDto {

    private Long tradeNo;
    private String nick;
    private String title;
    private String postStatus;
    private int view;
    private String tradeImg;
    private Long reviewCount;
    private Long wishCount;
    @JsonFormat(pattern = "yy-MM-dd hh:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yy-MM-dd hh:mm:ss")
    private LocalDateTime modifiedAt;

    @Builder
    public TradeResponseDto(Long tradeNo, String nick, String title, String postStatus, int view, String tradeImg,
                            Long wishCount, Long reviewCount, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.tradeNo = tradeNo;
        this.nick = nick;
        this.title = title;
        this.postStatus = postStatus;
        this.reviewCount = reviewCount;
        this.wishCount = wishCount;
        this.view = view;
        this.tradeImg = tradeImg;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
