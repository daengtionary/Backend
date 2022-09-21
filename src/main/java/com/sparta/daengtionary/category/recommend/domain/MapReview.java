package com.sparta.daengtionary.category.recommend.domain;


import com.sparta.daengtionary.aop.exception.CustomException;
import com.sparta.daengtionary.aop.exception.ErrorCode;
import com.sparta.daengtionary.category.member.domain.Member;
import com.sparta.daengtionary.category.recommend.dto.request.ReviewRequestDto;
import com.sparta.daengtionary.aop.util.Timestamped;
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

    @Builder
    public MapReview(Member member,Map map, String content,int star){
        this.member = member;
        this.map = map;
        this.content = content;
        this.star = star;
    }

    public void mapReviewUpdate(ReviewRequestDto requestDto){
        this.content = requestDto.getContent();
        this.star = requestDto.getStar();
    }

    public void validateMember(Member member) {
        if (!this.member.equals(member)) {
            throw new CustomException(ErrorCode.MAP_WRONG_ACCESS);
        }
    }
}
