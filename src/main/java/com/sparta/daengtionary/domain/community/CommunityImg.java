package com.sparta.daengtionary.domain.community;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class CommunityImg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long communityImgNo;
    @JoinColumn(name = "communityNo", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Community community;
    @Column
    private String communityImg;


    @Builder
    public CommunityImg(Long communityImgNo, Community community, String communityImg) {
        this.communityImgNo = communityImgNo;
        this.community = community;
        this.communityImg = communityImg;
    }
}