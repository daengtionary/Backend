package com.sparta.daengtionary.category.recommend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparta.daengtionary.aop.exception.CustomException;
import com.sparta.daengtionary.aop.exception.ErrorCode;
import com.sparta.daengtionary.aop.util.Timestamped;
import com.sparta.daengtionary.category.member.domain.Member;
import com.sparta.daengtionary.category.recommend.dto.request.MapPutRequestDto;
import com.sparta.daengtionary.category.wish.domain.Wish;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Map extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mapNo;

    @JoinColumn(name = "memberNo", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String content;

    @Column
    private float star;

    @Column
    private int view;

    @Column(nullable = false)
    private String address;

    @JsonIgnore
    @OneToMany(mappedBy = "map", fetch = FetchType.LAZY)
    private List<MapImg> mapImgs;

    @JsonIgnore
    @OneToMany(mappedBy = "map", fetch = FetchType.LAZY)
    private List<MapInfo> mapInfos;

    @JsonIgnore
    @OneToMany(mappedBy = "map", fetch = FetchType.LAZY)
    private List<MapReview> mapReviews;

    @JsonIgnore
    @OneToMany(mappedBy = "map", fetch = FetchType.LAZY)
    private List<Wish> wishes;

    @Builder
    public Map(Long mapNo, Member member, String title, String category, String content,
               String address) {
        this.mapNo = mapNo;
        this.member = member;
        this.title = title;
        this.category = category;
        this.content = content;
        this.star = 0;
        this.view = 0;
        this.address = address;
    }

    public void updateMap(MapPutRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.category = requestDto.getCategory();
        this.content = requestDto.getContent();
        this.address = requestDto.getAddress();
    }

    public void validateMember(Member member) {
        if (!this.member.equals(member)) {
            throw new CustomException(ErrorCode.MAP_WRONG_ACCESS);
        }
    }

    public void viewUpdate() {
        this.view += 1;
    }

    public void starUpdate(float star) {
        this.star = star;
    }

}
