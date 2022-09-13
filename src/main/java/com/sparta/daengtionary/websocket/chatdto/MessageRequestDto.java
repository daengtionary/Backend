package com.sparta.daengtionary.websocket.chatdto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MessageRequestDto {
    // 메시지 타입 : 입장, 채팅
    private Long roomId;
    private Long senderId;
    private String nickname;
    private String message;
    private String type;
    private Boolean isRead;
}