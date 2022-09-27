package com.sparta.daengtionary.category.recommend.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class MapDetailTestResponseDto {
    private Long mapNo;
    private String nick;
    private String title;
    private String address;
    private String category;
    private String content;
    private float mapStar;
    private int view;
    @JsonFormat(pattern = "yy-MM-dd hh:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yy-MM-dd hh:mm:ss")
    private LocalDateTime modifiedAt;

    @Builder
    public MapDetailTestResponseDto(Long mapNo, String nick, String title, String address,
                                    String category, String content, float mapStar, int view,
                                    LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.mapNo = mapNo;
        this.nick = nick;
        this.title = title;
        this.address = address;
        this.category = category;
        this.content = content;
        this.mapStar = mapStar;
        this.view = view;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;

    }
}
