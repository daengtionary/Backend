package com.sparta.daengtionary.category.community.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sparta.daengtionary.category.member.domain.Member;
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
    @JsonFormat(pattern = "yy-MM-dd hh:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yy-MM-dd hh:mm:ss")
    private LocalDateTime modifiedAt;

    @Builder
    public CommunityDetatilResponseDto(Long communityNo, String title, String content, int view, String category, Member breed, Long reviewCount, Long wishCount,
                                       String nick , List<String> imgList, List<ReviewResponseDto> reviewList , LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.communityNo = communityNo;
        this.nick = nick;
        this.title = title;
        this.content = content;
        this.category = category;
        this.reviewCount = reviewCount;
        this.wishCount = wishCount;
        this.view = view;
        if(breed.getDogs().size() != 0){
            this.breed = breed.getDogs().get(0).getBreed();
        }
        this.imgList = imgList;
        this.reviewList = reviewList;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
