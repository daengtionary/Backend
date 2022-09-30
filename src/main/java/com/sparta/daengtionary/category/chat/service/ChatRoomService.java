package com.sparta.daengtionary.category.chat.service;

import com.sparta.daengtionary.aop.dto.ResponseBodyDto;
import com.sparta.daengtionary.aop.exception.CustomException;
import com.sparta.daengtionary.aop.jwt.TokenProvider;
import com.sparta.daengtionary.category.chat.domain.ChatMessage;
import com.sparta.daengtionary.category.chat.domain.ChatRoom;
import com.sparta.daengtionary.category.chat.domain.ChatRoomMember;
import com.sparta.daengtionary.category.chat.dto.request.ChatRoomRequestDto;
import com.sparta.daengtionary.category.chat.dto.response.ChatRoomResponseDto;
import com.sparta.daengtionary.category.chat.repository.ChatMessageRepository;
import com.sparta.daengtionary.category.chat.repository.ChatRoomMemberRepository;
import com.sparta.daengtionary.category.chat.repository.ChatRoomRepository;
import com.sparta.daengtionary.category.member.domain.Member;
import com.sparta.daengtionary.category.member.dto.response.MemberResponseDto;
import com.sparta.daengtionary.category.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static com.sparta.daengtionary.aop.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private final TokenProvider tokenProvider;
    private final MemberService memberService;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomMemberRepository chatRoomMemberRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final ResponseBodyDto responseBodyDto;

    @Transactional
    public ResponseEntity<?> createPersonalChatRoom(HttpServletRequest request,
                                                    ChatRoomRequestDto requestDto) {
        // creator 정보
        Member creator = tokenProvider.getMemberFromAuthentication();

        // target 정보
        Member target = memberService.checkMemberByMemberNo(requestDto.getMemberNo());

        // creator, target 같으면 Exception
        if (creator.equals(target)) {
            throw new CustomException(CANNOT_CHAT_WITH_ME);
        }

        // chatRoom 있으면 가져오고 없으면 생성 후 member 저장
        ChatRoom chatRoom = checkPersonalChatRoomByMembers(creator, target);

        // welcome message 저장
        chatMessageRepository.save(ChatMessage.createMessageWelcome(chatRoom.getRoomNo()));

        return responseBodyDto.success(ChatRoomResponseDto.builder()
                        .roomNo(chatRoom.getRoomNo())
                        .build(),
                "채팅방 준비 완료");
    }

    @Transactional
    public ResponseEntity<?> createGroupChatRoom(HttpServletRequest request,
                                                 ChatRoomRequestDto requestDto) {
        // 생성 member 정보
        Member member = tokenProvider.getMemberFromAuthentication();

        // chatRoom 생성
        ChatRoom chatRoom = chatRoomRepository.save(ChatRoom.createChatRoom("group", requestDto.getTitle()));

        // chatRoom member 정보 저장
        chatRoomMemberRepository.save(ChatRoomMember.createChatRoomMember(chatRoom, member));

        return responseBodyDto.success(ChatRoomResponseDto.builder()
                        .roomNo(chatRoom.getRoomNo())
                        .build(),
                "채팅방 준비 완료");
    }

    @Transactional
    public ResponseEntity<?> createGroupChatRoomMember(HttpServletRequest request,
                                                       ChatRoomRequestDto requestDto) {
        // 참여 요청 member 정보
        Member member = tokenProvider.getMemberFromAuthentication();

        // 참여 요청 chatRoom 정보
        ChatRoom chatRoom = getChatRoomByRoomNo(requestDto.getRoomNo());

        // 참여 여부 확인 후 미참여 시 참여
        ChatRoomMember chatRoomMember = checkChatRoomMemberByMemberAndChatRoom(member, chatRoom);

        return responseBodyDto.success("참여가 완료되었습니다 :)");
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> getChatRooms(HttpServletRequest request) {
        // member 정보
        Member member = tokenProvider.getMemberFromAuthentication();

        // chatRoom 찾기
        List<ChatRoom> chatRoomList = chatRoomRepository.findByAllChatRoom(member);
        List<ChatRoomResponseDto> chatRoomResponseDtoList = new ArrayList<>();

        for (ChatRoom chatRoom : chatRoomList) {
            List<ChatRoomMember> chatRoomMemberList = chatRoomMemberRepository.findByChatRoom(chatRoom);
            List<MemberResponseDto> memberResponseDtoList = new ArrayList<>();

            // chatRoom 참여자 정보 가져오기
            for (ChatRoomMember chatRoomMember : chatRoomMemberList) {
                memberResponseDtoList.add(
                        MemberResponseDto.builder()
                                .memberNo(chatRoomMember.getMember().getMemberNo())
                                .nick(chatRoomMember.getMember().getNick())
                                .build()
                );
            }

            // 마지막 채팅 메세지 가져오기
            ChatMessage chatMessage = chatMessageRepository.findTop1ByRoomNoOrderByMessageNoDesc(chatRoom.getRoomNo()).orElseThrow(
                    () -> new CustomException(NOT_FOUND_CHAT_ROOM)
            );

            // responseDto 추가
            chatRoomResponseDtoList.add(
                    ChatRoomResponseDto.builder()
                            .roomNo(chatRoom.getRoomNo())
                            .type(chatRoom.getType())
                            .chatRoomMembers(memberResponseDtoList)
                            .lastDate(chatMessage.getCreatedAt())
                            .lastMessage(chatMessage.getMessage())
                            .build()
            );
        }

        return responseBodyDto.success(chatRoomResponseDtoList, "채팅방 조회 완료");
    }


    @Transactional
    public ChatRoom checkPersonalChatRoomByMembers(Member creator, Member target) {
        return chatRoomRepository.findByChatRoom(creator, target).orElseGet(
                () -> {
                    // chatRoom 생성
                    ChatRoom chatRoom = chatRoomRepository.save(ChatRoom.createChatRoom("personal", "1:1"));

                    // 생성 및 대상 member 정보 저장
                    chatRoomMemberRepository.save(ChatRoomMember.createChatRoomMember(chatRoom, creator));
                    chatRoomMemberRepository.save(ChatRoomMember.createChatRoomMember(chatRoom, target));

                    return chatRoom;
                }
        );
    }

    @Transactional
    public ChatRoomMember checkChatRoomMemberByMemberAndChatRoom(Member member, ChatRoom chatRoom) {
        return chatRoomMemberRepository.findByMemberAndChatRoom(member, chatRoom).orElseGet(
                () -> chatRoomMemberRepository.save(ChatRoomMember.createChatRoomMember(chatRoom, member))
        );
    }

    @Transactional(readOnly = true)
    public ChatRoom getChatRoomByRoomNo(Long roomNo) {
        return chatRoomRepository.findById(roomNo).orElseThrow(
                () -> new CustomException(NOT_FOUND_CHAT_ROOM)
        );
    }

    @Transactional(readOnly = true)
    public ChatRoomMember getChatRoomByMemberAndChatRoom(Member member, ChatRoom chatRoom) {
        return chatRoomMemberRepository.findByMemberAndChatRoom(member, chatRoom).orElseThrow(
                () -> new CustomException(NOT_FOUND_CHAT_ROOM_MEMBER)
        );
    }
}