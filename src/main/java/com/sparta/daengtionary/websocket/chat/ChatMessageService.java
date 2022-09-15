package com.sparta.daengtionary.websocket.chat;


import com.sparta.daengtionary.configration.S3Uploader;
import com.sparta.daengtionary.domain.Member;
import com.sparta.daengtionary.repository.MemberRepository;
import com.sparta.daengtionary.websocket.chatdto.MessageRequestDto;
import com.sparta.daengtionary.websocket.chatdto.MessageResponseDto;
import com.sparta.daengtionary.websocket.chatdto.RoomMsgUpdateDto;
import com.sparta.daengtionary.websocket.chatroom.ChatRoom;
import com.sparta.daengtionary.websocket.chatroom.ChatRoomRepository;
import com.sparta.daengtionary.websocket.redis.RedisMessagePublisher;
import com.sparta.daengtionary.websocket.sse.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatMessageService {

    private final RedisMessagePublisher redisMessagePublisher;
    private final ChatRoomRepository roomRepository;
    private final ChatMessageRepository messageRepository;
    private final MemberRepository memberRepository;
    private final SimpMessageSendingOperations messagingTemplate;
    private final NotificationService notificationService;

    private final RedisTemplate redisTemplate;

    private final S3Uploader s3Uploader;

    private final String imageDirName = "chatMessage";

    private Map<Long, Integer> roomMembers;


    @PostConstruct
    private void init() {
        roomMembers = new HashMap<>();
    }

    // 메시지 찾기, 페이징 처리
    public List<MessageResponseDto> getMessage(Long roomId, Long memberid , String nickname) {

        System.out.println("5555555555555555555555555555555555555 메시지찾기 getMessage nickname = " + nickname);
        // 메시지 찾아오기
        List<ChatMessage> messages = messageRepository.findAllByChatRoomNoOrderByChatRoomNoAsc(roomId);
        // responseDto 만들기


        List<MessageResponseDto> responseDtos = new ArrayList<>();
        // 상대가 보낸 메시지라면 모두 읽음으로 처리 -> isRead 상태 모두 true로 업데이트
        messageRepository.updateChatMessage(roomId, memberid);
        for (ChatMessage message : messages) {
//            message.update();
            responseDtos.add(MessageResponseDto.createFrom(message));
        }
        return responseDtos;
    }


    //    region 채팅방 사진 메시지 보내기
    @Transactional
    public void uploadChatMessageImg(MultipartFile img, MessageRequestDto requestDto) {

        ChatRoom chatRoom = messageRepository.findByChatRoomNo(requestDto.getChatRoomNo());

        Member member = memberRepository.findById(requestDto.getSenderNo()).orElseThrow(
                () -> new IllegalArgumentException("해당하는 유저가 존재하지 않습니다")
        );

        Member receiver = chatRoom.getAcceptor();

        String imageUrl;
        try {
            imageUrl = s3Uploader.upload((File) img, imageDirName);
        } catch (Exception err) {
            imageUrl = "No Message Image";
        }

        ChatMessage message = new ChatMessage(requestDto.getChatRoomNo(), requestDto.getSenderNo() , requestDto.getMessage());

        message.setImg(imageUrl);

        messageRepository.save(message);

        System.out.println("전송");
        // pub -> 채널 구독자에게 전달
        redisMessagePublisher.publish(requestDto);
        // 알림 보내기
        notificationService.send(receiver);
        System.out.println("성공");
    }


    // 채팅 메시지 및 알림 저장하기
    @Transactional
    public MessageResponseDto saveMessage(MessageRequestDto requestDto,
                                          String membername,
                                          String nickname) {

        MessageRequestDto sendMessageDto = new MessageRequestDto();


        System.out.println("requestDto.getRoomId() = " + requestDto.getChatRoomNo());
        ChatRoom chatRoom = roomRepository.findByIdFetch(requestDto.getChatRoomNo()).orElseThrow(
                () -> new NullPointerException("해당 채팅방이 존재하지 않습니다.")
        );
        chatRoom.accOut(false);
        chatRoom.reqOut(false);

        System.out.println("chatRoomchatRoomchatRoomchatRoomchatRoomchatRoomchatRoomchatRoomchatRoom = " + chatRoom);

        Member receiver = chatRoom.getAcceptor();
        Member sender = chatRoom.getRequester();
        ChatMessage message = messageRepository.save(ChatMessage.createOf(requestDto, membername , nickname));
        // pub -> 채널 구독자에게 전달
        redisMessagePublisher.publish(requestDto);
        // 알림 보내기
        notificationService.send(receiver);
        notificationService.sender(sender);

        return MessageResponseDto.createOf(message, membername , nickname);
    }


    // 채팅 메시지 발송하기
    @Transactional
    public void sendMessage(MessageRequestDto requestDto, String memberId, MessageResponseDto responseDto) {
        RoomMsgUpdateDto msgUpdateDto = RoomMsgUpdateDto.createFrom(requestDto);
        MessageRequestDto sendMessageDto = new MessageRequestDto();
        redisMessagePublisher.publish(requestDto);

        messagingTemplate.convertAndSend("/sub/chat/rooms/" + memberId, msgUpdateDto); // 개별 채팅 목록 보기 업데이트
        messagingTemplate.convertAndSend("/sub/chat/room/" + requestDto.getChatRoomNo(), responseDto); // 채팅방 내부로 메시지 전송
    }

}
