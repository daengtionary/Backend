package com.sparta.daengtionary.dto.response.community;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sparta.daengtionary.dto.response.ReviewResponseDto;
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
    private String content;
    private int view;
    private List<String> imgList;
    private List<ReviewResponseDto> reviewList;
    @JsonFormat(pattern = "yy-MM-dd hh:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yy-MM-dd hh:mm:ss")
    private LocalDateTime modifiedAt;

    @Builder
    public CommunityDetatilResponseDto(Long communityNo, String title, String content, int view, String category,
                                       String nick , List<String> imgList,List<ReviewResponseDto> reviewList ,LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.communityNo = communityNo;
        this.nick = nick;
        this.title = title;
        this.content = content;
        this.category = category;
        this.view = view;
        this.imgList = imgList;
        this.reviewList = reviewList;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
