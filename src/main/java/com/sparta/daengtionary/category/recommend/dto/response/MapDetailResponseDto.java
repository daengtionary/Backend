package com.sparta.daengtionary.category.recommend.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sparta.daengtionary.category.recommend.domain.MapImg;
import com.sparta.daengtionary.category.recommend.domain.MapReview;
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
    private Long reviewCount;
    private Long wishCount;
    private List<String> imgUrls;
    private List<String> mapInfo;

    private String info;
    private String mapImgUrl;

    private Long reviewNo;

    private String reviewContent;

    private int reviewStar;

    private MapImg mapImg;

    @JsonFormat(pattern = "yy-MM-dd hh:mm:ss")
    private LocalDateTime reviewCreatedAt;

    @JsonFormat(pattern = "yy-MM-dd hh:mm:ss")
    private LocalDateTime reviewModifiedAt;
    private List<ReviewResponseDto> mapReviewList;
    @JsonFormat(pattern = "yy-MM-dd hh:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yy-MM-dd hh:mm:ss")
    private LocalDateTime modifiedAt;


    @Builder
    public MapDetailResponseDto(Long mapNo, String nick, String title, String address, String category,
                                MapImg mapImg, String content, Long reviewCount, Long wishCount, List<ReviewResponseDto> mapReviewList,
                                float mapStar, int view, List<String> imgUrls, List<String> mapInfo,
                                LocalDateTime createdAt, LocalDateTime modifiedAt, Long reviewNo, String reviewContent,
                                String mapImgUrl,String info,int reviewStar, LocalDateTime reviewCreatedAt, LocalDateTime reviewModifiedAt) {
        this.mapNo = mapNo;
        this.nick = nick;
        this.title = title;
        this.address = address;
        this.category = category;
        this.content = content;
        this.mapStar = mapStar;
        this.mapImg = mapImg;
        this.view = view;
        this.imgUrls = imgUrls;
        this.mapInfo = mapInfo;
        this.reviewCount = reviewCount;
        this.wishCount = wishCount;
        this.mapReviewList = mapReviewList;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.reviewNo = reviewNo;
        this.reviewContent = reviewContent;
        this.reviewStar = reviewStar;
        this.reviewCreatedAt = reviewCreatedAt;
        this.reviewModifiedAt = reviewModifiedAt;
        this.mapImgUrl = mapImgUrl;
        this.info = info;
    }

}
