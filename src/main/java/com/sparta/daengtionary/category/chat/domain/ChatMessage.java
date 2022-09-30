package com.sparta.daengtionary.category.chat.domain;

import com.sparta.daengtionary.aop.util.Timestamped;
import com.sparta.daengtionary.category.chat.dto.request.MessageRequestDto;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class ChatMessage extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long messageNo;

    @Column
    private Long roomNo;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String sender;

    @Column(nullable = false)
    private String message;


    public ChatMessage createMessageWelcome(Long roomNo) {
        ChatMessage chatMessage = new ChatMessage();

        chatMessage.roomNo = roomNo;
        chatMessage.type = "SYSTEM";
        chatMessage.message = "대화가 시작되었습니다 :)";
        chatMessage.sender = "SYSTEM";

        return chatMessage;
    }

    public static ChatMessage createMessageEnter(MessageRequestDto requestDto) {
        ChatMessage chatMessage = new ChatMessage();

        chatMessage.roomNo = requestDto.getRoomNo();
        chatMessage.type = requestDto.getType();
        chatMessage.message = requestDto.getSender() + "님이 입장하였습니다 :)";
        chatMessage.sender = requestDto.getSender();

        return chatMessage;
    }

    public static ChatMessage createMessageTalk(MessageRequestDto requestDto) {
        ChatMessage chatMessage = new ChatMessage();

        chatMessage.roomNo = requestDto.getRoomNo();
        chatMessage.type = requestDto.getType();
        chatMessage.message = requestDto.getMessage();
        chatMessage.sender = requestDto.getSender();

        return chatMessage;
    }
}