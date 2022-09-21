package com.sparta.daengtionary.category.chat.service;

import com.sparta.daengtionary.aop.dto.ResponseBodyDto;
import com.sparta.daengtionary.aop.jwt.TokenProvider;
import com.sparta.daengtionary.category.chat.domain.ChatMessage;
import com.sparta.daengtionary.category.chat.dto.request.MessageRequestDto;
import com.sparta.daengtionary.category.chat.dto.response.MessageResponseDto;
import com.sparta.daengtionary.category.chat.repository.ChatMessageRepository;
import com.sparta.daengtionary.category.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageService {
    private final TokenProvider tokenProvider;
    private final ChatMessageRepository chatMessageRepository;
    private final ResponseBodyDto responseBodyDto;
    private final SimpMessageSendingOperations sendingOperations;

    public ResponseEntity<?> getChatMessages(HttpServletRequest request, Long chatNo) {
        List<ChatMessage> messageList = chatMessageRepository.findAllByRoomNo(chatNo);

        return responseBodyDto.success("메세지 조회 완료");
    }

    @Transactional
    public void sendMessage(HttpServletRequest request,
                            MessageRequestDto requestDto) {
        Member member = tokenProvider.getMemberFromAuthentication();

        ChatMessage chatMessage = chatMessageRepository.save(ChatMessage.createMessage(requestDto, member));

        MessageResponseDto responseDto = MessageResponseDto.createMessage(chatMessage);

        sendingOperations.convertAndSend("/topic/chat/room/" + requestDto.getRoomNo(), responseDto);
    }
}