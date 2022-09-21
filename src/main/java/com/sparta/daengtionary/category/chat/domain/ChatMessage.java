package com.sparta.daengtionary.category.chat.domain;

import com.sparta.daengtionary.aop.util.Timestamped;
import com.sparta.daengtionary.category.chat.dto.request.MessageRequestDto;
import com.sparta.daengtionary.category.member.domain.Member;
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

    @JoinColumn(name = "senderNo")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member sender;

    @Column(nullable = false)
    private String content;


    public static ChatMessage createMessage(MessageRequestDto requestDto, Member member) {
        ChatMessage chatMessage = new ChatMessage();

        chatMessage.roomNo = requestDto.getRoomNo();
        chatMessage.type = requestDto.getType();
        chatMessage.content = requestDto.getContent();
        chatMessage.sender = member;

        return chatMessage;
    }
}