package com.sparta.daengtionary.category.recommend.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MapImgResponseDto {
    private String mapImgUrl;

    @Builder
    public MapImgResponseDto(String mapImgUrl) {
        this.mapImgUrl = mapImgUrl;
    }
}


