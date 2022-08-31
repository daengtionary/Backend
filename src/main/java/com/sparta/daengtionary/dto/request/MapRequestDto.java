package com.sparta.daengtionary.dto.request;

import lombok.Getter;

import java.util.List;

@Getter
public class MapRequestDto {
    private String title;
    private String category;
    private String content;
    private int star;
    private int view;
    private String address;
    private int mapx;
    private int mapy;
    private List<String> imgUrls;
    private List<String> mapInfos;
}