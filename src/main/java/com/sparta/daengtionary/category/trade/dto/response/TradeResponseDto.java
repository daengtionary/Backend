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
    private Long wishCount;
    @JsonFormat(pattern = "yyyy년 MM월 dd일 E요일 a hh:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy년 MM월 dd일 E요일 a hh:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime modifiedAt;

    @Builder
    public TradeResponseDto(Long tradeNo, String nick, String title, String postStatus, int view, String tradeImg,
                            Long wishCount, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.tradeNo = tradeNo;
        this.nick = nick;
        this.title = title;
        this.postStatus = postStatus;
        this.wishCount = wishCount;
        this.view = view;
        this.tradeImg = tradeImg;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
