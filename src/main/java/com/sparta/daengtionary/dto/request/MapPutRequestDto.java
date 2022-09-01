package com.sparta.daengtionary.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MapPutRequestDto {
    private String title;
    private String content;

    public MapPutRequestDto(){
    }
}
