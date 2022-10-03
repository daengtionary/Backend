package com.sparta.daengtionary.category.friend.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class FriendImg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long friendImgNo;

    @JoinColumn(name = "tradeNo", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Friend friend;

    @Column
    private String friendImg;

    @Builder
    public FriendImg(Long friendImgNo, Friend friend, String friendImg) {
        this.friendImgNo = friendImgNo;
        this.friend = friend;
        this.friendImg = friendImg;
    }
}