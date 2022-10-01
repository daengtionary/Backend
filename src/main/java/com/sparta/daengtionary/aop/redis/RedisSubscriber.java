package com.sparta.daengtionary.aop.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.daengtionary.category.chat.dto.request.MessageRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class RedisSubscriber implements MessageListener {
    private final ObjectMapper objectMapper;
    private final RedisTemplate redisTemplate;
    private final SimpMessageSendingOperations messagingTemplate;

    // Redis에서 메시지가 발행(publish)되면 대기하고 있던 onMessage가 해당 메시지를 받아 처리한다.
    @Override
    public void onMessage(Message message, byte[] pattern) {
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!들어옴?");
        //메시지를 받아와 ChatMessage로 변환 -> messaging Template를 이용하여 채팅방의 모든 웹소켓 클라이언트에게 전달.
        try {
            // redis에서 발행된 데이터를 받아 deserialize
            String publishMessage = (String) redisTemplate.getStringSerializer().deserialize(message.getBody());
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!publishMessage = " + publishMessage);

            // MessageDto 객체로 매핑
            MessageRequestDto requestDto = objectMapper.readValue(publishMessage, MessageRequestDto.class);
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!chatMessage = " + requestDto);

            // 웹 소켓 구독자에게 채팅 메시지 send
            messagingTemplate.convertAndSend("/sub/chat/room/" + requestDto.getRoomNo(), requestDto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}