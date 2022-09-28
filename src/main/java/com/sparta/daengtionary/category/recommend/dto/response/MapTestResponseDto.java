package com.sparta.daengtionary.category.recommend.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class MapTestResponseDto {
    private MapDetailTestResponseDto mapDetailTestResponseDto;
    private List<MapImgResponseDto> mapImgResponseDtoList;
    private List<ReviewResponseDto> reviewResponseDtoList;

    @Builder
    public MapTestResponseDto(MapDetailTestResponseDto mapDetailTestResponseDto, List<MapImgResponseDto> mapImgResponseDtoList,
                              List<ReviewResponseDto> reviewResponseDtoList) {
        this.mapDetailTestResponseDto = mapDetailTestResponseDto;
        this.mapImgResponseDtoList = mapImgResponseDtoList;
        this.reviewResponseDtoList = reviewResponseDtoList;
    }


}
