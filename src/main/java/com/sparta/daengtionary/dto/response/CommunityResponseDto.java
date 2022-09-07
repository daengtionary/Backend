package com.sparta.daengtionary.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class CommunityResponseDto {
    private Long communityNo;
    private MemberResponseDto memberResponseDto;
    private String title;
    private String content;
    private int view;
    private List<String> communityImgUrl;
    @JsonFormat(pattern = "yy-MM-dd hh:mm:ss")
    private LocalDateTime createAt;
    @JsonFormat(pattern = "yy-MM-dd hh:mm:ss")
    private LocalDateTime modifiedAt;

    @Builder
    public CommunityResponseDto(Long communityNo, String title, String content, int view,
                                MemberResponseDto memberResponseDto ,List<String> communityImgUrl, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.communityNo = communityNo;
        this.memberResponseDto = memberResponseDto;
        this.title = title;
        this.content = content;
        this.view = view;
        this.communityImgUrl = communityImgUrl;
        this.createAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
