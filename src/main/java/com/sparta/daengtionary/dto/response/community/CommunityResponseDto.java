package com.sparta.daengtionary.dto.response.community;

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
    private String title;
    private int view;
    private String communityImg;
    private Long reviewCount;
    @JsonFormat(pattern = "yy-MM-dd hh:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yy-MM-dd hh:mm:ss")
    private LocalDateTime modifiedAt;


    @Builder
    public CommunityResponseDto(Long communityNo, String nick, String title, int view, String communityImg,
                                Long reviewCount,LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.communityNo = communityNo;
        this.nick = nick;
        this.title = title;
        this.view = view;
        this.reviewCount = reviewCount;
        this.communityImg = communityImg;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
