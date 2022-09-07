package com.sparta.daengtionary.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class MapDetailResponseDto {
    private Long mapNo;
    private String nick;
    private String title;
    private String address;
    private String category;
    private String content;
    private float star;
    private int view;
    private List<String> imgUrls;
    private List<String> mapInfo;
    @JsonFormat(pattern = "yy-MM-dd hh:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yy-MM-dd hh:mm:ss")
    private LocalDateTime moditiedAt;


    @Builder
    public MapDetailResponseDto(Long mapNo, String nick, String title, String address, String category,
                                String content, float star, int view, List<String> imgUrls, List<String> mapInfo, LocalDateTime createdAt, LocalDateTime moditiedAt) {
        this.mapNo = mapNo;
        this.nick = nick;
        this.title = title;
        this.address = address;
        this.category = category;
        this.content = content;
        this.star = star;
        this.view = view;
        this.imgUrls = imgUrls;
        this.mapInfo = mapInfo;
        this.createdAt = createdAt;
        this.moditiedAt = moditiedAt;
    }

}
