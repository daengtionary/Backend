package com.sparta.daengtionary.category.community.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CommunityDetatilResponseDto {
    private Long communityNo;
    private String nick;
    private String title;
    private String category;
    private String breed;
    private String image;
    private String content;
    private int view;
    @JsonFormat(pattern = "yyyy년 MM월 dd일 E요일 a hh:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy년 MM월 dd일 E요일 a hh:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime modifiedAt;

    @Builder
    public CommunityDetatilResponseDto(Long communityNo, String title, String content, int view, String category, String breed,
                                       String image, String nick, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.communityNo = communityNo;
        this.nick = nick;
        this.title = title;
        this.category = category;
        this.breed = breed;
        this.image = image;
        this.content = content;
        this.view = view;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

}
