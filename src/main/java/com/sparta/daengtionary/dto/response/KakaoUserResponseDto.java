package com.sparta.daengtionary.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class KakaoUserResponseDto {
    private String email;
    private String nick;


    @Builder
    public KakaoUserResponseDto(String email, String nick) {
        this.email = email;
        this.nick = nick;
    }
}