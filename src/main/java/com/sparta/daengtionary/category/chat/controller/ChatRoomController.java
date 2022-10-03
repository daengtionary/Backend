package com.sparta.daengtionary.category.chat.controller;

import com.sparta.daengtionary.category.chat.dto.request.ChatRoomRequestDto;
import com.sparta.daengtionary.category.chat.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatRoomController {
    private final ChatRoomService chatRoomService;

    // 1:1 채팅방 생성
    @PostMapping("/room/personal")
    public ResponseEntity<?> createChatRoomPersonal(HttpServletRequest request,
                                                    @RequestBody ChatRoomRequestDto requestDto) {
        return chatRoomService.createChatRoomPersonal(request, requestDto);
    }

    // 채팅방 가져오기
    @GetMapping("/rooms")
    public ResponseEntity<?> getChatRooms(HttpServletRequest request) {
        return chatRoomService.getChatRooms(request);
    }
}