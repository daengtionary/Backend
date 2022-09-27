package com.sparta.daengtionary.category.chat.repository;

import com.sparta.daengtionary.category.chat.domain.ChatRoom;
import com.sparta.daengtionary.category.chat.domain.ChatRoomMember;
import com.sparta.daengtionary.category.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRoomMemberRepository extends JpaRepository<ChatRoomMember, Long> {
    List<ChatRoomMember> findByChatRoom(ChatRoom chatRoom);
    Optional<ChatRoomMember> findByMemberAndChatRoom(Member member, ChatRoom chatRoom);
}