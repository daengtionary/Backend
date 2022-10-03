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

    // chatRoom message 가져오기
    @GetMapping("/chat/messages/{roomNo}")
    public ResponseEntity<?> getMessages(HttpServletRequest request,
                                         @PathVariable Long roomNo) {
        return chatMessageService.getMessages(request, roomNo);
    }

    // message 보내기
    @MessageMapping("/chat/message")
    public void sendMessage(MessageRequestDto requestDto) {
        chatMessageService.sendMessage(requestDto);
    }
}