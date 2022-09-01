package com.sparta.daengtionary.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MapImgResponseDto {
    private String imgName;
    private String imgUrl;

    @Builder
    public MapImgResponseDto(String imgName,String imgUrl){
        this.imgName = imgName;
        this.imgUrl = imgUrl;
    }
}
