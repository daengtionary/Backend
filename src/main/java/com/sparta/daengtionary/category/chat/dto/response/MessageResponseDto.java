package com.sparta.daengtionary.category.chat.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sparta.daengtionary.category.chat.domain.ChatMessage;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MessageResponseDto {
    private Long messageNo;

    private String type;

    private String sender;

    private String message;

    @JsonFormat(pattern = "yyyy년 MM월 dd일 E요일 a hh:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime date;
    
    
    public MessageResponseDto() {
        
    }
    
    @Builder
    public MessageResponseDto(Long messageNo, String type, String sender, String message, LocalDateTime date) {
        this.messageNo = messageNo;
        this.type = type;
        this.sender = sender;
        this.message = message;
        this.date = date;
    }


    public MessageResponseDto createMessage(ChatMessage chatMessage) {
        MessageResponseDto responseDto = new MessageResponseDto();

        responseDto.messageNo = chatMessage.getMessageNo();
        responseDto.type = chatMessage.getType();
        responseDto.sender = chatMessage.getSender();
        responseDto.message = chatMessage.getMessage();
        responseDto.date = chatMessage.getCreatedAt();

        return responseDto;
    }
}