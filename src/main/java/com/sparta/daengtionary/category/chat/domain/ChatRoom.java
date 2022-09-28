package com.sparta.daengtionary.category.chat.domain;

import com.sparta.daengtionary.aop.util.Timestamped;
import com.sparta.daengtionary.category.member.domain.Member;
import lombok.Getter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
public class ChatRoom extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomNo;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String title;

    @OneToMany(mappedBy = "chatRoom")
    private List<ChatRoomMember> chatRoomMembers;


    public ChatRoom() {

    }

    public static ChatRoom createChatRoom(String type, String title) {
        ChatRoom chatRoom = new ChatRoom();

            chatRoom.type = type;
            chatRoom.title = title;

        return chatRoom;
    }
}