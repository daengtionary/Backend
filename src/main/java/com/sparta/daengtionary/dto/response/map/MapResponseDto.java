package com.sparta.daengtionary.dto.response.map;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class MapResponseDto {
    private Long mapNo;
    private String category;
    private String title;
    private String address;
    private float star;
    private String mapImgUrl;
    private String mapInfo;
    private Long reviewCount;
    @JsonFormat(pattern = "yy-MM-dd hh:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yy-MM-dd hh:mm:ss")
    private  LocalDateTime modifiedAt;

    @Builder
    public MapResponseDto(Long mapNo,String category,String title,String address,float star,
                          String mapImgUrl,String mapInfo,Long reviewCount,LocalDateTime createdAt, LocalDateTime modifiedAt){
        this.mapNo = mapNo;
        this.category = category;
        this.title = title;
        this.address = address;
        this.star = star;
        this.mapImgUrl = mapImgUrl;
        this.mapInfo = mapInfo;
        this.reviewCount = reviewCount;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
