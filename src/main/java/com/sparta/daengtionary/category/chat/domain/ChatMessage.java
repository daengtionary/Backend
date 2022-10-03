package com.sparta.daengtionary.category.chat.domain;

import com.sparta.daengtionary.aop.util.Timestamped;
import com.sparta.daengtionary.category.chat.dto.request.MessageRequestDto;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class ChatMessage extends Timestamped {
    private static final long serialVersionUID = 6494678977089006639L;

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


    public ChatMessage() {

    }

    @Builder
    public ChatMessage(Long messageNo, Long roomNo, String type, String message, String sender) {
        this.messageNo = messageNo;
        this.roomNo = roomNo;
        this.type = type;
        this.sender = sender;
        this.message = message;
    }
}