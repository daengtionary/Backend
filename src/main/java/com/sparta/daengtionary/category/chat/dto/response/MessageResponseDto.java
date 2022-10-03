package com.sparta.daengtionary.category.chat.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sparta.daengtionary.category.chat.domain.ChatMessage;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MessageResponseDto {
    private Long messageNo;

    private Long roomNo;

    private String type;

    private String sender;

    private String message;

    @JsonFormat(pattern = "yyyy년 MM월 dd일 E요일 a hh:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime date;
    
    
    public MessageResponseDto() {
        
    }
    
    @Builder
    public MessageResponseDto(Long messageNo, Long roomNo, String type, String sender, String message, LocalDateTime date) {
        this.messageNo = messageNo;
        this.roomNo = roomNo;
        this.type = type;
        this.sender = sender;
        this.message = message;
        this.date = date;
    }
}