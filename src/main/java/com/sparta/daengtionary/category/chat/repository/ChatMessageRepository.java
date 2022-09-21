package com.sparta.daengtionary.category.chat.repository;

import com.sparta.daengtionary.category.chat.domain.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findAllByRoomNo(Long roomNo);
}