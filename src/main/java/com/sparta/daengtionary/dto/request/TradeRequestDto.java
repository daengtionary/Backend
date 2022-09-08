package com.sparta.daengtionary.dto.request;

import lombok.Getter;

@Getter
public class TradeRequestDto {
    private String title;
    private String content;
    private int price;
    private String status;
}
