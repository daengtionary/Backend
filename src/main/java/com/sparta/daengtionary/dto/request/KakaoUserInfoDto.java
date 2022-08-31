package com.sparta.daengtionary.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class KakaoUserInfoDto {
    private Long kakaoId;
    private String email;
    private String nick;


    public KakaoUserInfoDto() {

    }

    @Builder
    public KakaoUserInfoDto(Long kakaoId, String email, String nick) {
        this.kakaoId = kakaoId;
        this.email = email;
        this.nick = nick;
    }

}