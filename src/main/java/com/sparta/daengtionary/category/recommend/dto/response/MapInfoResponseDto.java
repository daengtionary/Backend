package com.sparta.daengtionary.category.recommend.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MapInfoResponseDto {
    private String mapInfo;

    public MapInfoResponseDto(String mapInfo){
        this.mapInfo = mapInfo;
    }
}
