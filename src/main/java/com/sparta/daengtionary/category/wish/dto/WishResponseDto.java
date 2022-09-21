package com.sparta.daengtionary.category.wish.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WishResponseDto {

    private Long mapNo;

    private Long comNo;

    private Long tradeNo;

    @Builder
    public WishResponseDto(Long mapNo,Long comNo,Long tradeNo){
        this.mapNo = mapNo;
        this.comNo = comNo;
        this.tradeNo = tradeNo;
    }
}
