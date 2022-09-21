package com.sparta.daengtionary.category.member.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class KakaoUserResponseDto {
    private Long memberNo;
    private String email;
    private String nick;


    @Builder
    public KakaoUserResponseDto(Long memberNo, String email, String nick) {
        this.memberNo = memberNo;
        this.email = email;
        this.nick = nick;
    }
}