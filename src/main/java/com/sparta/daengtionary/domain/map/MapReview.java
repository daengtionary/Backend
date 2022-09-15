package com.sparta.daengtionary.domain.map;


import com.sparta.daengtionary.configration.error.CustomException;
import com.sparta.daengtionary.configration.error.ErrorCode;
import com.sparta.daengtionary.domain.Member;
import com.sparta.daengtionary.dto.request.ReviewRequestDto;
import com.sparta.daengtionary.util.Timestamped;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class MapReview extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mapReviewNo;

    @JoinColumn(name = "memberNo", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @JoinColumn(name = "mapNo",nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Map map;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private int star;

    @Column
    private String imgUrl;

    @Builder
    public MapReview(Member member,Map map, String content,int star,String imgUrl){
        this.member = member;
        this.map = map;
        this.content = content;
        this.star = star;
        this.imgUrl = imgUrl;
    }

    public void mapReviewUpdate(ReviewRequestDto requestDto, String imgUrl){
        this.content = requestDto.getContent();
        this.star = requestDto.getStar();
        this.imgUrl = imgUrl;
    }

    public void validateMember(Member member) {
        if (!this.member.equals(member)) {
            throw new CustomException(ErrorCode.MAP_WRONG_ACCESS);
        }
    }
}
