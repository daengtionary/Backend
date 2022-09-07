package com.sparta.daengtionary.dto.response.trade;

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
    private String title;
    private String content;
    private String status;
    private int price;
    private int view;
    private List<String> tradeImgUrl;
    @JsonFormat(pattern = "yy-MM-dd hh:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yy-MM-dd hh:mm:ss")
    private LocalDateTime modifiedAt;

    @Builder
    public TradeDetailResponseDto(Long tradeNo, String nick, String title,
                                  String content, int price, String status, int view, List<String> tradeImgUrl,
                                  LocalDateTime createdAt, LocalDateTime modifiedAt){
        this.tradeNo = tradeNo;
        this.nick = nick;
        this.title = title;
        this.content = content;
        this.price = price;
        this.status = status;
        this.view = view;
        this.tradeImgUrl = tradeImgUrl;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }


}
