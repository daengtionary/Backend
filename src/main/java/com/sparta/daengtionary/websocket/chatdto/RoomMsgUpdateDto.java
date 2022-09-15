package com.sparta.daengtionary.websocket.chatdto;


import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class RoomMsgUpdateDto {

    private Long roomId;
    private String message;
    private LocalDateTime date;

    public static RoomMsgUpdateDto createFrom(MessageRequestDto requestDto){
        RoomMsgUpdateDto msgUpdateDto = new RoomMsgUpdateDto();

        msgUpdateDto.roomId = requestDto.getChatRoomNo();
        msgUpdateDto.message = requestDto.getMessage();
        msgUpdateDto.date = LocalDateTime.now();

        return msgUpdateDto;
    }
}