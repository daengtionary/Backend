package com.sparta.daengtionary.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TokenDto {
    private String grantType;
    private String accessToken;
    private Long accessTokenExpiresIn;


    @Builder
    public TokenDto(String grantType, String accessToken, Long accessTokenExpiresIn) {
        this.grantType = grantType;
        this.accessToken = accessToken;
        this.accessTokenExpiresIn = accessTokenExpiresIn;
    }

    public TokenDto() {
    }

}