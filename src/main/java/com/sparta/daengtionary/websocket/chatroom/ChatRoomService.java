package com.sparta.daengtionary.websocket.chatroom;

import com.sparta.daengtionary.configration.error.CustomException;
import com.sparta.daengtionary.configration.error.ErrorCode;
import com.sparta.daengtionary.domain.Member;
import com.sparta.daengtionary.dto.request.MemberRequestDto;
import com.sparta.daengtionary.dto.response.ResponseBodyDto;
import com.sparta.daengtionary.jwt.TokenProvider;
import com.sparta.daengtionary.repository.MemberRepository;
import com.sparta.daengtionary.websocket.chat.ChatMessage;
import com.sparta.daengtionary.websocket.chat.ChatMessageRepository;
import com.sparta.daengtionary.websocket.chatdto.MessageResponseDto;
import com.sparta.daengtionary.websocket.chatdto.RoomDto;
import com.sparta.daengtionary.websocket.chatdto.RoomResponseDto;
import com.sparta.daengtionary.websocket.chatdto.response.ChatRoomResponseDto;
import com.sparta.daengtionary.websocket.sse.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.sparta.daengtionary.configration.error.ErrorCode.*;
import static com.sparta.daengtionary.websocket.chatroom.ChatRoomService.MemberTypeEnum.Type.REQUESTER;
import static com.sparta.daengtionary.websocket.chatroom.ChatRoomService.MemberTypeEnum.Type.ACCEPTOR;

@RequiredArgsConstructor
@Service
public class ChatRoomService {
    private final NotificationRepository notificationRepository;
    private final ChatRoomRepository roomRepository;
    private final ChatMessageRepository messageRepository;
    private final ChatMessage chatMessage;
    private final SimpMessageSendingOperations messagingTemplate;
    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;
    private final ResponseBodyDto responseBodyDto;

    // 채팅방 만들기
    public ResponseEntity<?> createChatRoom(HttpServletRequest request,
                                            MemberRequestDto.MakeChat makeChat) {
        // requester member 정보
        Member requester = tokenProvider.getMemberFromAuthentication();

        // acceptor member 정보
        Member acceptor = checkMemberByMemberNo(makeChat.getMemberNo());

        // 요청자와 응답자가 같을경우 error
        if (requester.equals(acceptor)) {
            throw new CustomException(CANNOT_CHAT_WITH_ME);
        }

        // 채팅방 찾기, 없을 경우 생성
        ChatRoom chatRoom = checkChatRoomByMember(requester, acceptor);

        // 채팅방 입장
        chatRoom.enter();

        return responseBodyDto.success(ChatRoomResponseDto.Create.builder()
                        .chatRoomNo(chatRoom.getChatRoomNo())
                        .build(),
                "채탱방 생성 완료"
        );
    }


    @Transactional
    public Long createRoom(Long memberid, Long acceptorId) {
        // 유효성 검사
        if (memberid.equals(acceptorId)) {
            throw new CustomException(CANNOT_CHAT_WITH_ME);
        }
        // 채팅 상대 찾아오기
        Member acceptor = memberRepository.findById(acceptorId)
                .orElseThrow(() -> new CustomException(NOT_FOUND_USER_INFO)
                );
        Member requester = memberRepository.findById(memberid)
                .orElseThrow(() -> new CustomException(NOT_FOUND_USER_INFO)
                );
        // 채팅방을 찾아보고, 없을 시 DB에 채팅방 저장
        ChatRoom chatRoom = roomRepository.findByMember(requester, acceptor)
                .orElseGet(() -> {
                    ChatRoom c = roomRepository.save(ChatRoom.createOf(requester, acceptor));
//               // 채팅방 개설 메시지 생성
//                    notificationRepository.save(Notification.createOf(c, acceptor)); // 알림 작성 및 전달
//                    messagingTemplate.convertAndSend("/sub/notification/" + acceptorId,
//                            MessageResponseDto.createFrom(
//                                    messageRepository.save(ChatMessage.createInitOf(c.getId()))
//                            )
//                    );
                    return c;
                });
        chatRoom.enter(); // 채팅방에 들어간 상태로 변경 -> 람다를 사용해 일괄처리할 방법이 있는지 연구해 보도록 합니다.

        return chatRoom.getChatRoomNo();
    }


    // 방을 나간 상태로 변경하기
    @Transactional
    public void exitRoom(Long id, Long memberid) {
        // 회원 찾기
        Member member = memberRepository.findById(memberid)
                .orElseThrow(() -> new CustomException(NOT_FOUND_USER_INFO)
                );
        // 채팅방 찾아오기
        ChatRoom chatRoom = roomRepository.findByIdFetch(id).orElseThrow(
                () -> new NullPointerException("해당 아이디가 존재하지 않습니다.")
        );

        if (chatRoom.getRequester().getMemberNo().equals(memberid)) {
            chatRoom.reqOut(true);
        } else if (chatRoom.getAcceptor().getMemberNo().equals(memberid)) {
            chatRoom.accOut(true);
        } else {
            throw new CustomException(EXIT_INVAILED);
        }

        if (chatRoom.getAccOut() && chatRoom.getReqOut()) {
            roomRepository.deleteById(chatRoom.getChatRoomNo()); // 둘 다 나간 상태라면 방 삭제
        } else {
            // 채팅방 종료 메시지 전달 및 저장
            messagingTemplate.convertAndSend("/sub/chat/room/" + chatRoom.getChatRoomNo(),
                    MessageResponseDto.createFrom(
                            messageRepository.save(ChatMessage.createOutOf(id, member))
                    )
            );
        }
    }

    // 사용자별 채팅방 전체 목록 가져오기
    public List<RoomResponseDto> getRooms(Long memberId, String nickname) {
        // 회원 찾기
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new NullPointerException("해당 아이디가 존재하지 않습니다.")
        );
        // 방 목록 찾기
        List<RoomDto> dtos = roomRepository.findAllWith(member);
        // 메시지 리스트 만들기
        return getMessages(dtos, memberId, nickname);
    }

    public List<RoomResponseDto> getMessages(List<RoomDto> roomDtos, Long memberId, String nickname) {

        List<RoomResponseDto> prefix = new ArrayList<>();
        List<RoomResponseDto> suffix = new ArrayList<>();

        String type = chatMessage.getType();

        for (RoomDto dto : roomDtos) {
            // 해당 방의 유저가 나가지 않았을 경우에는 배열에 포함해 줍니다.
            if (dto.getAccId().equals(memberId)) {
                if (!dto.getAccOut()) { // 만약 Acc(내)가 나가지 않았다면
                    int unreadCnt = messageRepository.countMsg(dto.getReqId(), dto.getRoomId());
                    if (dto.getAccFixed()) {
                        prefix.add(RoomResponseDto.createOf(type, ACCEPTOR, dto, unreadCnt, false));
                    } else {
                        suffix.add(RoomResponseDto.createOf(type, ACCEPTOR, dto, unreadCnt, false));
                    }
                }
            } else if (dto.getReqId().equals(memberId)) {
                if (!dto.getReqOut()) { // 만약 Req(내)가 나가지 않았다면
                    int unreadCnt = messageRepository.countMsg(dto.getAccId(), dto.getRoomId());
                    if (dto.getReqFixed()) {
                        prefix.add(RoomResponseDto.createOf(type, REQUESTER, dto, unreadCnt, false));

                    } else {
                        suffix.add(RoomResponseDto.createOf(type, REQUESTER, dto, unreadCnt, false));
                    }
                }
            }
        }
        prefix.addAll(suffix);
        return prefix;
    }

    public enum MemberTypeEnum {
        ACCEPTOR(Type.ACCEPTOR),
        REQUESTER(Type.REQUESTER);

        private final String memberType;

        MemberTypeEnum(String memberType) {
            this.memberType = memberType;
        }

        public static class Type {
            public static final String ACCEPTOR = "ACCEPTOR";
            public static final String REQUESTER = "REQUESTER";
        }
    }

    @Transactional(readOnly = true)
    public Member checkMemberByMemberNo(Long memberNo) {
        Optional<Member> optionalMember = memberRepository.findById(memberNo);

        return optionalMember.orElseThrow(
                () -> new CustomException(NOT_FOUND_USER_INFO)
        );
    }

    @Transactional
    public ChatRoom checkChatRoomByMember(Member requester, Member acceptor) {
        Optional<ChatRoom> optionalChatRoom = roomRepository.findByMember(requester, acceptor);

        return optionalChatRoom.orElseGet(
                () -> roomRepository.save(ChatRoom.createOf(requester, acceptor))
        );
    }
}