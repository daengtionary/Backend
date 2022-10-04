package com.sparta.daengtionary.category.friend.dto.request;

import lombok.Getter;

@Getter
public class FriendRequestDto {
    private Long friendNo;
    private String address;
    private String category;
    private String title;
    private String content;
    private int maxCount;
}
