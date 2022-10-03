package com.sparta.daengtionary.category.community.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommunityReviewResponseDto {

    private Long reviewNo;
    private String nick;
    private String content;
    private String image;


    @Builder
    public CommunityReviewResponseDto(Long reviewNo, String nick, String content, String image) {
        this.reviewNo = reviewNo;
        this.nick = nick;
        this.content = content;
        this.image = image;
    }
}
