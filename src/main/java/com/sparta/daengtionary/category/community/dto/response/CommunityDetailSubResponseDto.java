package com.sparta.daengtionary.category.community.dto.response;


import com.sparta.daengtionary.category.recommend.dto.response.ImgResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class CommunityDetailSubResponseDto {

    private CommunityDetatilResponseDto communityDetatilResponseDto;
    private List<ImgResponseDto> imgResponseDtoList;
    private List<CommunityReviewResponseDto> reviewResponseDtos;

    @Builder
    public CommunityDetailSubResponseDto(CommunityDetatilResponseDto communityDetatilResponseDto,List<ImgResponseDto> imgResponseDtoList,
                                         List<CommunityReviewResponseDto> reviewResponseDtos){
        this.communityDetatilResponseDto = communityDetatilResponseDto;
        this.imgResponseDtoList = imgResponseDtoList;
        this.reviewResponseDtos = reviewResponseDtos;
    }

}
