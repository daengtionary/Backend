package com.sparta.daengtionary.dto.response.map;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MapReviewResponseDto {
    private Long mapReviewNo;
    private String nick;
    private String content;
    private int star;
    private String imgUrl;

    @Builder
    public MapReviewResponseDto(Long mapReviewNo,String nick,String content,int star,String imgUrl){
        this.mapReviewNo = mapReviewNo;
        this.nick = nick;
        this.content = content;
        this.star = star;
        this.imgUrl = imgUrl;
    }

}
