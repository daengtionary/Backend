package com.sparta.daengtionary.category.chat.domain;

import com.sparta.daengtionary.category.member.domain.Member;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class ChatRoomMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatMemberNo;
    @JoinColumn(name = "chatNo", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private ChatPersonalRoom chatPersonalRoom;
    @JoinColumn(name = "memberNo", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;


    public ChatRoomMember() {

    }


    public static ChatRoomMember createChatRoomMember(ChatPersonalRoom chatPersonalRoom, Member member) {
        ChatRoomMember chatRoomMember = new ChatRoomMember();

        chatRoomMember.chatPersonalRoom = chatPersonalRoom;
        chatRoomMember.member = member;

        return chatRoomMember;
    }
}