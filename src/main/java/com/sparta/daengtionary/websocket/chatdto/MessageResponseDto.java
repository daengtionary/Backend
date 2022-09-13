package com.sparta.daengtionary.websocket.chatdto;

import com.sparta.daengtionary.websocket.chat.ChatMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MessageResponseDto {


    private Long messageId;
    private Long senderId;
    private String senderName;
    private String senderNickname;
    private String message;
    private LocalDateTime date;
    private Boolean isRead = false;
    private String type;

    private String img;

    public MessageResponseDto(MessageRequestDto messageRequestDto) {
        this.messageId = Long.valueOf(messageRequestDto.getMessage());
        this.senderId = messageRequestDto.getSenderId();
        this.senderNickname = messageRequestDto.getNickname();
        this.message = messageRequestDto.getMessage();
        this.isRead = messageRequestDto.getIsRead();
        this.type = messageRequestDto.getType();

    }

    public static MessageResponseDto createOf(ChatMessage message, String username, String nickname){

        MessageResponseDto responseDto = new MessageResponseDto();

        responseDto.senderName = username;
        responseDto.messageId = message.getId();
        responseDto.message = message.getMessage();
        responseDto.date = message.getCreatedAt();
        responseDto.type = message.getType();
        responseDto.senderNickname = nickname;

        return responseDto;
    }

    public static MessageResponseDto createFrom(ChatMessage message){

        MessageResponseDto responseDto = new MessageResponseDto();

        responseDto.senderId = message.getSenderId();
        responseDto.messageId = message.getId();
        responseDto.message = message.getMessage();
        responseDto.date = message.getCreatedAt();
        responseDto.type = message.getType();
        responseDto.isRead = true;
        responseDto.senderName = message.getSenderName();
        responseDto.senderNickname = message.getSenderNickname();

        return responseDto;

    }

}
