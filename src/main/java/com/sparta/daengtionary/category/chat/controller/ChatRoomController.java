package com.sparta.daengtionary.category.chat.controller;

import com.sparta.daengtionary.category.chat.dto.request.ChatRoomRequestDto;
import com.sparta.daengtionary.category.chat.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/chat/rooms")
@RequiredArgsConstructor
public class ChatRoomController {
    private final ChatRoomService chatRoomService;

    // 1:1 채팅방 생성
    @PostMapping()
    public ResponseEntity<?> createChatPersonalRoom(HttpServletRequest request,
                                                    @RequestBody ChatRoomRequestDto requestDto) {
        return chatRoomService.createChatPersonalRoom(request, requestDto);
    }

    // 1:1 채팅방 목록 가져오기
    @GetMapping()
    public ResponseEntity<?> getChatPersonalRooms(HttpServletRequest request) {
        return chatRoomService.getChatPersonalRooms(request);
    }
}