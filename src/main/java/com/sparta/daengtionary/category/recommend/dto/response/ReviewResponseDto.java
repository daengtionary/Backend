package com.sparta.daengtionary.category.recommend.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewResponseDto {
    private Long reviewNo;
    private String nick;
    private String content;
    private String image;
    private int star;

    @Builder
    public ReviewResponseDto(Long reviewNo, String nick, String content, int star, String image) {
        this.reviewNo = reviewNo;
        this.nick = nick;
        this.content = content;
        this.star = star;
        this.image = image;
    }

    @Builder
    public ReviewResponseDto(Long reviewNo, String nick, String content, String image) {
        this.reviewNo = reviewNo;
        this.nick = nick;
        this.content = content;
        this.image = image;
    }

}
