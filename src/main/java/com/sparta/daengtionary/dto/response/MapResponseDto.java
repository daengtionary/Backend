package com.sparta.daengtionary.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MapResponseDto {
    private Long mapNo;
    private String title;
    private String address;
    private String category;
    private float star;
    private String imgUrls;

    private String mapInfos;

    @JsonFormat(pattern = "yy-MM-dd hh:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yy-MM-dd hh:mm:ss")
    private  LocalDateTime modifiedAt;


}
