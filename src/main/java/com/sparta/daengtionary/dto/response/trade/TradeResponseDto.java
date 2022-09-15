package com.sparta.daengtionary.dto.response.trade;

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
    private String status;
    private String category;
    private int view;
    private String tradeImg;
    private Long reviewCount;

    private Long wishCount;
    @JsonFormat(pattern = "yy-MM-dd hh:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yy-MM-dd hh:mm:ss")
    private LocalDateTime modifiedAt;

    @Builder
    public TradeResponseDto(Long tradeNo, String nick, String title, String status, String category, int view, String tradeImg,
                            Long reviewCount, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.tradeNo = tradeNo;
        this.nick = nick;
        this.title = title;
        this.status = status;
        this.category = category;
        this.reviewCount = reviewCount;
        this.view = view;
        this.tradeImg = tradeImg;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
