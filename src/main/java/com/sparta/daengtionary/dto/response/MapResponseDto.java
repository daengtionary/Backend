package com.sparta.daengtionary.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class MapResponseDto {
    private Long mapId;
    private String title;
    private String address;
    private String category;
    private int star;
    private List<String> imgUrls;

    private List<String> mapInfos;

    @JsonFormat(pattern = "yy-MM-dd hh:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yy-MM-dd hh:mm:ss")
    private  LocalDateTime modifiedAt;

    @Builder
    public MapResponseDto(Long mapId,String title,String address,String category,int star,
                          List<String> imgUrls,List<String> mapInfos,LocalDateTime createdAt,LocalDateTime modifiedAt){
        this.mapId = mapId;
        this.title = title;
        this.address = address;
        this.category = category;
        this.star = star;
        this.imgUrls = imgUrls;
        this.mapInfos = mapInfos;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

}
