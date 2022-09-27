package com.sparta.daengtionary.category.recommend.dto.response;

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
    private float mapStar;
    private int view;
    private List<String> imgUrls;
    private List<String> mapInfo;
    private List<ReviewResponseDto> mapReviewList;
    @JsonFormat(pattern = "yyyy년 MM월 dd일 E요일 a hh:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy년 MM월 dd일 E요일 a hh:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime modifiedAt;


    @Builder
    public MapDetailResponseDto(Long mapNo, String nick, String title, String address, String category,
                                String content, List<ReviewResponseDto> mapReviewList,
                                float mapStar, int view, List<String> imgUrls, List<String> mapInfo,
                                LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.mapNo = mapNo;
        this.nick = nick;
        this.title = title;
        this.address = address;
        this.category = category;
        this.content = content;
        this.mapStar = mapStar;
        this.view = view;
        this.imgUrls = imgUrls;
        this.mapInfo = mapInfo;
        this.mapReviewList = mapReviewList;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

}
