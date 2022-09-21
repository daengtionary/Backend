package com.sparta.daengtionary.category.chat.domain;

import com.sparta.daengtionary.category.member.domain.Member;
import com.sparta.daengtionary.aop.util.Timestamped;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class ChatPersonalRoom extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatNo;

    @Column(nullable = false)
    private String title;

    @JoinColumn(nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member creator;

    @JoinColumn(nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member target;


    public ChatPersonalRoom() {

    }

    public static ChatPersonalRoom createChatPersonalRoom(Member creator, Member target) {
        ChatPersonalRoom chatPersonalRoom = new ChatPersonalRoom();

        chatPersonalRoom.title = "personal";
        chatPersonalRoom.creator = creator;
        chatPersonalRoom.target = target;

        return chatPersonalRoom;
    }
}