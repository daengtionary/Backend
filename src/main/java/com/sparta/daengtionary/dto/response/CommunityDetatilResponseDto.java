package com.sparta.daengtionary.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    private String content;
    private int view;
    private List<String> imgList;
    @JsonFormat(pattern = "yy-MM-dd hh:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yy-MM-dd hh:mm:ss")
    private LocalDateTime modifiedAt;

    @Builder
    public CommunityDetatilResponseDto(Long communityNo, String title, String content, int view,
                                       String nick , List<String> imgList, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.communityNo = communityNo;
        this.nick = nick;
        this.title = title;
        this.content = content;
        this.view = view;
        this.imgList = imgList;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
