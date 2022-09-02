package com.sparta.daengtionary.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sparta.daengtionary.domain.Member;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class MapDetailResponseDto {
    private Long mapNo;
    private MemberResponseDto member;
    private String title;
    private String address;
    private String category;
    private String content;
    private float star;
    private int view;
    private Double mapx;
    private Double mapy;
    private List<String> imgUrls;
    private List<String> mapInfo;
    @JsonFormat(pattern = "yy-MM-dd hh:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yy-MM-dd hh:mm:ss")
    private LocalDateTime moditiedAt;

    public MapDetailResponseDto() {

    }

    @Builder
    public MapDetailResponseDto(Long mapNo, MemberResponseDto member, String title, String address, String category,
                                String content, float star, int view, Double mapx, Double mapy, List<String> imgUrls, List<String> mapInfo, LocalDateTime createdAt, LocalDateTime moditiedAt) {
        this.mapNo = mapNo;
        this.member = member;
        this.title = title;
        this.address = address;
        this.category = category;
        this.content = content;
        this.star = star;
        this.view = view;
        this.mapx = mapx;
        this.mapy = mapy;
        this.imgUrls = imgUrls;
        this.mapInfo = mapInfo;
        this.createdAt = createdAt;
        this.moditiedAt = moditiedAt;
    }

}
