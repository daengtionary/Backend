package com.sparta.daengtionary.category.chat.service;

import com.sparta.daengtionary.aop.dto.ResponseBodyDto;
import com.sparta.daengtionary.aop.redis.RedisPublisher;
import com.sparta.daengtionary.category.chat.domain.ChatMessage;
import com.sparta.daengtionary.category.chat.domain.ChatRoom;
import com.sparta.daengtionary.category.chat.domain.ChatRoomMember;
import com.sparta.daengtionary.category.chat.dto.request.MessageRequestDto;
import com.sparta.daengtionary.category.chat.dto.response.MessageResponseDto;
import com.sparta.daengtionary.category.chat.repository.ChatMessageRepository;
import com.sparta.daengtionary.category.member.domain.Member;
import com.sparta.daengtionary.category.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageService {
    private final ChatMessageRepository chatMessageRepository;
    private final ResponseBodyDto responseBodyDto;
    private final MemberService memberService;
    private final ChatRoomService chatRoomService;
    private final RedisPublisher redisPublisher;

    // 메세지 가져오기
    public ResponseEntity<?> getMessages(HttpServletRequest request, Long roomNo) {
        // chatRoom message 전체조회
        List<ChatMessage> messageList = chatMessageRepository.findAllByRoomNo(roomNo);

        // response List 생성
        List<MessageResponseDto> messageResponseDtoList = new ArrayList<>();

        // response List에 추가
        for (ChatMessage chatMessage : messageList) {
            messageResponseDtoList.add(MessageResponseDto.builder()
                    .messageNo(chatMessage.getMessageNo())
                    .type(chatMessage.getType())
                    .sender(chatMessage.getSender())
                    .message(chatMessage.getMessage())
                    .date(chatMessage.getCreatedAt())
                    .build()
            );
        }

        return responseBodyDto.success(messageResponseDtoList, "메세지 조회 완료");
    }

    // 메세지 저장 & 보내기
    @Transactional
    public void sendMessage(MessageRequestDto requestDto) {
        if (requestDto.getType().equals("ENTER")) {
            // sender & chatroom 정보로 ChatRoomMember 가져오기
            Member sender = memberService.checkMemberByNick(requestDto.getSender());
            ChatRoom chatRoom = chatRoomService.getChatRoomByRoomNo(requestDto.getRoomNo());
            ChatRoomMember chatRoomMember = chatRoomService.getChatRoomByMemberAndChatRoom(sender, chatRoom);

            // enterStatus가 false일 경우만 저장
            if (!chatRoomMember.getEnterStatus()) {
                // message 생성 & 저장
                ChatMessage chatMessage = ChatMessage.builder()
                        .roomNo(requestDto.getRoomNo())
                        .type(requestDto.getType())
                        .sender(requestDto.getSender())
                        .message(requestDto.getSender() + "님이 입장하였습니다 :)")
                        .build();

                chatMessageRepository.save(chatMessage);

                // enterStatus true로 변경
                chatRoomMember.updateEnterStatus();

                // responseDto에 담기
                MessageResponseDto responseDto = MessageResponseDto.builder()
                        .messageNo(chatMessage.getMessageNo())
                        .roomNo(chatMessage.getRoomNo())
                        .type(chatMessage.getType())
                        .sender(chatMessage.getSender())
                        .message(chatMessage.getMessage())
                        .date(chatMessage.getCreatedAt())
                        .build();

                // 메세지 보내기
                redisPublisher.publish(responseDto);
            }
        } else if (requestDto.getType().equals("TALK")) {
            // message 생성 & 저장
            ChatMessage chatMessage = ChatMessage.builder()
                    .roomNo(requestDto.getRoomNo())
                    .type(requestDto.getType())
                    .sender(requestDto.getSender())
                    .message(requestDto.getMessage())
                    .build();

            chatMessageRepository.save(chatMessage);

            // responseDto에 담기
            MessageResponseDto responseDto = MessageResponseDto.builder()
                    .messageNo(chatMessage.getMessageNo())
                    .messageNo(chatMessage.getMessageNo())
                    .type(chatMessage.getType())
                    .sender(chatMessage.getSender())
                    .message(chatMessage.getMessage())
                    .date(chatMessage.getCreatedAt())
                    .build();

            // 메세지 보내기
            redisPublisher.publish(responseDto);
        }
    }
}