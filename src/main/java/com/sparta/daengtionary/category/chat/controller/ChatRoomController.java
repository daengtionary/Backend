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
    @PostMapping("/room/personal")
    public ResponseEntity<?> createPersonalChatRoom(HttpServletRequest request,
                                                    @RequestBody ChatRoomRequestDto requestDto) {
        return chatRoomService.createPersonalChatRoom(request, requestDto);
    }

    @PostMapping("/room/into")
    public ResponseEntity<?> createGroupChatRoomMember(HttpServletRequest request,
                                                       @RequestBody ChatRoomRequestDto requestDto) {
        return chatRoomService.createGroupChatRoomMember(request, requestDto);
    }

    @GetMapping("/rooms")
    public ResponseEntity<?> getChatRooms(HttpServletRequest request) {
        return chatRoomService.getChatRooms(request);
    }
}