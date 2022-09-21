package com.sparta.daengtionary.category.chat.controller;

import com.sparta.daengtionary.category.chat.dto.request.MessageRequestDto;
import com.sparta.daengtionary.category.chat.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class ChatMessageController {
    private final ChatMessageService chatMessageService;

    // 채팅방 메세지 가져오기
    @GetMapping("/room/{chatNo}")
    public ResponseEntity<?> getChatMessages(HttpServletRequest request,
                                             @PathVariable Long chatNo) {
        return chatMessageService.getChatMessages(request, chatNo);
    }

    @MessageMapping("/chat/message")
    public void sendMessage(HttpServletRequest request,
                            MessageRequestDto requestDto) {
        chatMessageService.sendMessage(request, requestDto);
    }
}