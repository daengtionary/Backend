package com.sparta.daengtionary.websocket.chat;


import com.sparta.daengtionary.websocket.chatroom.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    List<ChatMessage> findAllByRoomIdOrderByIdAsc(Long roomId);

    // 메시지 안읽은 갯수 카운트 쿼리
    @Query("SELECT count(msg) FROM ChatMessage msg WHERE msg.senderId =:memberId AND msg.roomId =:roomId AND msg.isRead = false")
    int countMsg(Long memberId, Long roomId);

    // 채팅 메시지 읽음 상태 일괄 업데이트
    @Modifying
    @Transactional
    @Query("UPDATE ChatMessage msg SET msg.isRead = true WHERE msg.roomId = :roomId AND msg.senderId <> :memberId AND msg.isRead = false ")
    void updateChatMessage(Long roomId, Long memberId);

    ChatRoom findByRoomId(Long roomId);
}