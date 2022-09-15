package com.sparta.daengtionary.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sparta.daengtionary.domain.Wish;
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
