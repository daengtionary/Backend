package com.sparta.daengtionary.category.community.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sparta.daengtionary.category.recommend.dto.response.ReviewResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class CommunityDetatilResponseDto {
    private Long communityNo;
    private String nick;
    private String title;
    private String category;
    private String breed;
    private String content;
    private int view;
    private Long reviewCount;
    private Long wishCount;
    private List<String> imgList;
    private List<ReviewResponseDto> reviewList;
    @JsonFormat(pattern = "yyyy년 MM월 dd일 E요일 a hh:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy년 MM월 dd일 E요일 a hh:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime modifiedAt;

    @Builder
    public CommunityDetatilResponseDto(Long communityNo, String title, String content, int view, String category, String breed,Long reviewCount,Long wishCount,
                                       String nick , List<String> imgList,List<ReviewResponseDto> reviewList ,LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.communityNo = communityNo;
        this.nick = nick;
        this.title = title;
        this.content = content;
        this.category = category;
        this.reviewCount = reviewCount;
        this.wishCount = wishCount;
        this.view = view;
        this.breed = breed;
        this.imgList = imgList;
        this.reviewList = reviewList;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
