package com.sparta.daengtionary.category.chat.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sparta.daengtionary.category.chat.domain.ChatMessage;
import com.sparta.daengtionary.category.member.domain.Member;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MessageResponseDto {
    private Long messageNo;

    private String type;

    private Member sender;

    private String content;

    @JsonFormat(pattern = "yy-MM-dd hh:mm:ss")
    private LocalDateTime date;


    public static MessageResponseDto createMessage(ChatMessage chatMessage) {
        MessageResponseDto responseDto = new MessageResponseDto();

        responseDto.messageNo = chatMessage.getMessageNo();
        responseDto.type = chatMessage.getType();
        responseDto.sender = chatMessage.getSender();
        responseDto.content = chatMessage.getContent();
        responseDto.date = chatMessage.getCreatedAt();

        return responseDto;
    }
}