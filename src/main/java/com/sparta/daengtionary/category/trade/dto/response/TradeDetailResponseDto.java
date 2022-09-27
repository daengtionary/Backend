package com.sparta.daengtionary.category.trade.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sparta.daengtionary.category.recommend.dto.response.ReviewResponseDto;
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
    private String category;
    private int price;
    private int view;
    private Long reviewCount;
    private Long wishCount;
    private List<String> tradeImgUrl;
    private List<ReviewResponseDto> reviewList;
    @JsonFormat(pattern = "yyyy년 MM월 dd일 E요일 a hh:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy년 MM월 dd일 E요일 a hh:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime modifiedAt;

    @Builder
    public TradeDetailResponseDto(Long tradeNo, String nick, String title,Long wishCount,Long reviewCount,
                                  String content, int price, String status, String category, int view, List<String> tradeImgUrl,
                                  List<ReviewResponseDto> reviewList, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.tradeNo = tradeNo;
        this.nick = nick;
        this.title = title;
        this.content = content;
        this.price = price;
        this.wishCount = wishCount;
        this.reviewCount = reviewCount;
        this.status = status;
        this.category = category;
        this.view = view;
        this.reviewList = reviewList;
        this.tradeImgUrl = tradeImgUrl;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }


}
