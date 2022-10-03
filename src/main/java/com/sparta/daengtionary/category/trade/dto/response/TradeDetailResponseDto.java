package com.sparta.daengtionary.category.trade.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class TradeDetailResponseDto {
    private Long tradeNo;
    private String nick;
    private Long memberNo;
    private String title;
    private String address;
    private String stuffStatus;
    private String exchange;
    private String postStatus;
    private String content;
    private int price;
    private int view;
    private Long wishCount;
    private List<String> tradeImgUrl;
    @JsonFormat(pattern = "yyyy년 MM월 dd일 E요일 a hh:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy년 MM월 dd일 E요일 a hh:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime modifiedAt;

    @Builder
    public TradeDetailResponseDto(Long tradeNo, String nick, String title, Long wishCount,Long memberNo,
                                  String address, String stuffStatus, String exchange, String postStatus,
                                  String content, int price, int view, List<String> tradeImgUrl,
                                  LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.tradeNo = tradeNo;
        this.nick = nick;
        this.title = title;
        this.content = content;
        this.price = price;
        this.wishCount = wishCount;
        this.memberNo = memberNo;
        this.address = address;
        this.exchange = exchange;
        this.stuffStatus = stuffStatus;
        this.postStatus = postStatus;
        this.view = view;
        this.tradeImgUrl = tradeImgUrl;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }


}
