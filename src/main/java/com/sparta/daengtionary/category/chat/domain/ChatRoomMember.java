package com.sparta.daengtionary.category.chat.domain;

import com.sparta.daengtionary.category.member.domain.Member;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class ChatRoomMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomMemberNo;

    @JoinColumn(name = "roomNo", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private ChatRoom chatRoom;

    @JoinColumn(name = "memberNo", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Column(nullable = false)
    private Boolean enterStatus;


    public ChatRoomMember() {

    }


    public static ChatRoomMember createChatRoomMember(ChatRoom chatRoom, Member member) {
        ChatRoomMember chatRoomMember = new ChatRoomMember();

        chatRoomMember.chatRoom = chatRoom;
        chatRoomMember.member = member;
        chatRoomMember.enterStatus = false;

        return chatRoomMember;
    }

    public void updateEnterStatus() {
        this.enterStatus = true;
    }
}