package com.sparta.daengtionary.category.recommend.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class MapSubResponseDto {
    private MapDetailSubResponseDto mapDetailSubResponseDto;
    private List<ImgResponseDto> imgResponseDtoList;
    private List<ReviewResponseDto> reviewResponseDtoList;

    @Builder
    public MapSubResponseDto(MapDetailSubResponseDto mapDetailSubResponseDto, List<ImgResponseDto> imgResponseDtoList,
                             List<ReviewResponseDto> reviewResponseDtoList) {
        this.mapDetailSubResponseDto = mapDetailSubResponseDto;
        this.imgResponseDtoList = imgResponseDtoList;
        this.reviewResponseDtoList = reviewResponseDtoList;
    }


}
