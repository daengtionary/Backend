package com.sparta.daengtionary.category.community.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CommunityResponseDto {
    private Long communityNo;
    private String nick;
    private String breed;
    private String email;
    private String title;
    private String category;
    private int view;
    private String communityImg;
    private Long reviewCount;
    private Long wishCount;
    @JsonFormat(pattern = "yyyy년 MM월 dd일 E요일 a hh:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy년 MM월 dd일 E요일 a hh:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime modifiedAt;


    @Builder
    public CommunityResponseDto(Long communityNo, String nick, String title, int view, String communityImg, String category,String email,
                                String breed, Long wishCount, Long reviewCount, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.communityNo = communityNo;
        this.nick = nick;
        this.title = title;
        this.breed = breed;
        this.view = view;
        this.category = category;
        this.reviewCount = reviewCount;
        this.email = email;
        this.wishCount = wishCount;
        this.communityImg = communityImg;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
