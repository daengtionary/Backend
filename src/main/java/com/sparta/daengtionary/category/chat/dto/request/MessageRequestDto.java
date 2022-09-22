package com.sparta.daengtionary.category.chat.dto.request;

import lombok.Getter;

@Getter
public class MessageRequestDto {
    // 메시지 타입 : 입장, 채팅
    private Long roomNo;
    private String type;
    private String content;
}