package com.sparta.daengtionary.repository;

import com.sparta.daengtionary.domain.ChatRoomMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomMemberRepository extends JpaRepository<ChatRoomMember, Long> {
}
