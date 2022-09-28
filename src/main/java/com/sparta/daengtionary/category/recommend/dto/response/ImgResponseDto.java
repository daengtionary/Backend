package com.sparta.daengtionary.category.recommend.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ImgResponseDto {
    private String mapImgUrl;

    @Builder
    public ImgResponseDto(String mapImgUrl) {
        this.mapImgUrl = mapImgUrl;
    }
}


