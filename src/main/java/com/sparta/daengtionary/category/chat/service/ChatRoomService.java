package com.sparta.daengtionary.category.chat.service;

import com.sparta.daengtionary.aop.dto.ResponseBodyDto;
import com.sparta.daengtionary.aop.exception.CustomException;
import com.sparta.daengtionary.aop.jwt.TokenProvider;
import com.sparta.daengtionary.category.chat.domain.ChatRoom;
import com.sparta.daengtionary.category.chat.dto.request.ChatRoomRequestDto;
import com.sparta.daengtionary.category.chat.dto.response.ChatRoomResponseDto;
import com.sparta.daengtionary.category.chat.repository.ChatRoomRepository;
import com.sparta.daengtionary.category.member.domain.Member;
import com.sparta.daengtionary.category.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static com.sparta.daengtionary.aop.exception.ErrorCode.CANNOT_CHAT_WITH_ME;
import static com.sparta.daengtionary.aop.exception.ErrorCode.NOT_FOUND_USER_INFO;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private final TokenProvider tokenProvider;
    private final MemberRepository memberRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ResponseBodyDto responseBodyDto;

    @Transactional
    public ResponseEntity<?> createChatPersonalRoom(HttpServletRequest request,
                                                    ChatRoomRequestDto requestDto) {
        // 채탱방 생성 member 정보
        Member creator = tokenProvider.getMemberFromAuthentication();

        // 채팅방 대상 member 정보
        Member target = checkMemberByMemberNo(requestDto.getMemberNo());

        // 생성과 대싱이 같으면 Error
        if (creator.equals(target)) {
            throw new CustomException(CANNOT_CHAT_WITH_ME);
        }

        // 채팅방이 존재하면 가져오고 없으면 생성
        ChatRoom chatRoom = checkChatPersonalRoomByChatMember(creator, target);

        return responseBodyDto.success(ChatRoomResponseDto.builder()
                        .roomNo(chatRoom.getRoomNo())
                        .build(),
                "채팅방 준비 완료");
    }

    public ResponseEntity<?> getChatRooms(HttpServletRequest request) {
        // member 정보
        Member member = tokenProvider.getMemberFromAuthentication();

        // 1:1 채팅방 찾기
        List<ChatRoom> chatRoomList = chatRoomRepository.findByCreatorOrTarget(member);
        List<ChatRoomResponseDto> chatRoomResponseDtoList = new ArrayList<>();

        for (ChatRoom chatRoom : chatRoomList) {
            chatRoomResponseDtoList.add(
                    ChatRoomResponseDto.builder()
                            .roomNo(chatRoom.getRoomNo())
                            .type(chatRoom.getType())
                            .title(chatRoom.getTitle())
                            .creator(chatRoom.getCreator())
                            .target(chatRoom.getTarget())
                            .build()
            );
        }

        return responseBodyDto.success(chatRoomResponseDtoList, "1:1 채팅방 조회 완료");
    }


    @Transactional(readOnly = true)
    public Member checkMemberByMemberNo(Long memberNo) {
        return memberRepository.findById(memberNo).orElseThrow(
                () -> new CustomException(NOT_FOUND_USER_INFO)
        );
    }

    @Transactional
    public ChatRoom checkChatPersonalRoomByChatMember(Member creator, Member target) {
        return chatRoomRepository.findByCreatorAndTarget(creator, target).orElseGet(
                () -> chatRoomRepository.save(ChatRoom.createChatPersonalRoom(creator, target))
        );
    }
}