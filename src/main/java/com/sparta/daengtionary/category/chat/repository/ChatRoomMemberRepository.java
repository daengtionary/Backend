package com.sparta.daengtionary.category.chat.repository;

import com.sparta.daengtionary.category.chat.domain.ChatRoomMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomMemberRepository extends JpaRepository<ChatRoomMember, Long> {
}
