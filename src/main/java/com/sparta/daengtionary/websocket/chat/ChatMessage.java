package com.sparta.daengtionary.websocket.chat;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sparta.daengtionary.domain.Member;
import com.sparta.daengtionary.util.CreationDate;
import com.sparta.daengtionary.websocket.chatdto.MessageRequestDto;
import lombok.Getter;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Getter
@Entity
@Component
@JsonAutoDetect
//@NoArgsConstructor
//@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)

//@AllArgsConstructor
public class ChatMessage extends CreationDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private Long roomId;

    private String senderName;

    private Long senderId;

    private String senderNickname;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
//    @Enumerated(value = EnumType.STRING)
    private String type;

    @Column(nullable = false)
    private Boolean isRead;

    @Column
    private String img; // 이미지 첨부시





    public static ChatMessage createOf(MessageRequestDto requestDto, String senderName, String nickname) {

        ChatMessage message = new ChatMessage();

        message.senderId = requestDto.getSenderId();
        message.roomId = requestDto.getRoomId();
        message.senderName = senderName;
        message.message = requestDto.getMessage();
        message.isRead = requestDto.getIsRead();
        message.type = requestDto.getType();
        message.senderNickname = nickname;


        return message;
    }

    public ChatMessage(Long roomId, Long memberId, String message) {
        this.roomId = roomId;
        this.senderId = memberId;
        this.message = message;
    }


    public static ChatMessage createInitOf(Long roomId) {

        ChatMessage message = new ChatMessage();

        message.roomId = roomId;
        message.senderId = roomId;
//        message.message = "채팅방이 개설되었습니다.";
        message.isRead = true;
        message.type = "STATUS";

        return message;
    }

    public static ChatMessage createOutOf(Long roomId, Member member) {

        ChatMessage message = new ChatMessage();

        message.roomId = roomId;
//        message.senderId = member.getId();
        message.senderName = member.getMembername();
        message.message = member.getNickname()  + "님이 채팅방을 나갔습니다.";
        message.isRead = true;
        message.type = "STATUS";

        return message;
    }

    public ChatMessage(){

    }

    public void setImg(String img) {
        this.img = img;
    }

    public void update() {
        this.isRead = true;
    }
}

