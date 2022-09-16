package com.sparta.daengtionary.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewResponseDto {
    private Long reviewNo;
    private String nick;
    private String content;
    private String memberImgUrl;
    private int star;
    private String imgUrl;

    @Builder
    public ReviewResponseDto(Long reviewNo, String nick, String content, int star, String imgUrl, String memberImgUrl) {
        this.reviewNo = reviewNo;
        this.nick = nick;
        this.content = content;
        this.star = star;
        this.imgUrl = imgUrl;
        this.memberImgUrl = memberImgUrl;
    }

    @Builder
    public ReviewResponseDto(Long reviewNo, String nick, String content, String imgUrl, String memberImgUrl) {
        this.reviewNo = reviewNo;
        this.nick = nick;
        this.content = content;
        this.imgUrl = imgUrl;
        this.memberImgUrl = memberImgUrl;
    }

}
