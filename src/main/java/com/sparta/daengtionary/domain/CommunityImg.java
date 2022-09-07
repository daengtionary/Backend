package com.sparta.daengtionary.domain;

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

    @JoinColumn(name = "community_id",nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Community community;

    @Column
    private String communityImg;

    @Builder
    public CommunityImg(Community community, String communityImg){
        this.community = community;
        this.communityImg = communityImg;
    }

}
