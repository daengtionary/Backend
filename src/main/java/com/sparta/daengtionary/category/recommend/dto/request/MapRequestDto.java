package com.sparta.daengtionary.category.recommend.dto.request;

import lombok.Getter;

import java.util.List;

@Getter
public class MapRequestDto {
    private String title;
    private String category;
    private String content;
    private String address;
    private List<String> mapInfos;
}