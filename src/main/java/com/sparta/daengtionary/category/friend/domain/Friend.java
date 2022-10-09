package com.sparta.daengtionary.category.friend.domain;

import com.sparta.daengtionary.aop.exception.CustomException;
import com.sparta.daengtionary.aop.exception.ErrorCode;
import com.sparta.daengtionary.aop.util.Timestamped;
import com.sparta.daengtionary.category.chat.domain.ChatRoom;
import com.sparta.daengtionary.category.friend.dto.request.FriendRequestDto;
import com.sparta.daengtionary.category.member.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Friend extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long friendNo;
    @JoinColumn(name = "memberNo", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private String category;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String content;
    @Column(nullable = false)
    private String status;
    @Column(nullable = false)
    private int maxCount;
    @Column(nullable = false)
    private int count;
    @Column(nullable = false)
    private Long roomNo;


    @Builder
    public Friend(Member member, String address, String category, String title, String content, int maxCount, Long roomNo) {
        this.member = member;
        this.address = address;
        this.category = category;
        this.title = title;
        this.content = content;
        this.maxCount = maxCount;
        this.count = 0;
        this.status = "진행중";
        this.roomNo = roomNo;
    }

    public void firendUpdate(FriendRequestDto requestDto) {
        this.address = requestDto.getAddress();
        this.category = requestDto.getCategory();
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.maxCount = requestDto.getMaxCount();
    }

    public void validateMember(Member member) {
        if (!this.member.equals(member)) {
            throw new CustomException(ErrorCode.MAP_WRONG_ACCESS);
        }
    }

    public void NotOverCount(Member member) {
        if(this.count < this.maxCount && !this.member.equals(member)){
            setCount();
        }
        else if(this.member.equals(member)){
            throw new CustomException(ErrorCode.FRIEND_IS_NOT_MEMBER);
        }
    }

    public void finishCount(){
        if (this.count >= this.maxCount) {
            setStatus();
        }
    }

    public void setCount() {
        this.count += 1;
    }

    public void setStatus() {
        this.status = "마감 완료";
    }
}