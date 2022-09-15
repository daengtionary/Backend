package com.sparta.daengtionary.websocket.chatroom;


import com.sparta.daengtionary.domain.UserDetailsImpl;
import com.sparta.daengtionary.dto.request.MemberRequestDto;
import com.sparta.daengtionary.websocket.chat.ChatMessageService;
import com.sparta.daengtionary.websocket.chatdto.MessageRequestDto;
import com.sparta.daengtionary.websocket.chatdto.MessageResponseDto;
import com.sparta.daengtionary.websocket.chatdto.OkDto;
import com.sparta.daengtionary.websocket.chatdto.RoomResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/chat/rooms")
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomService chatRoomService;
    private final ChatRoomRepository roomRepository;
    private final ChatMessageService chatMessageService;


    // 채팅방 만들기
    @PostMapping()
    public ResponseEntity<?> createChatRoom(HttpServletRequest request,
                                            @RequestBody MemberRequestDto.MakeChat makeChat) {

        return chatRoomService.createChatRoom(request, makeChat);
    }

    // 전체 채팅방 목록 가져오기
    @GetMapping()
    public List<RoomResponseDto> getRooms(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long memberId = userDetails.getMemberId();
        String nickname = userDetails.getUsername();
        System.out.println(" 전체 채팅방 목록 가져오기 memberId = " + memberId);
        System.out.println(" 전체 채팅방 목록 가져오기 nickname = " + nickname);

        return chatRoomService.getRooms(memberId, nickname);
    }

    // 개별 채팅방 메시지 불러오기
    @GetMapping("/room/{roomId}")
    public List<MessageResponseDto> getMessage(@PathVariable Long roomId,
                                               @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long memberid = userDetails.getMemberId();
        String nickname = userDetails.getNickname();
        return chatMessageService.getMessage(roomId, memberid, nickname);
    }


    // 채팅방 나가기
    @GetMapping("/room/exit/{roomId}")
    public ResponseEntity<OkDto> exitRoom(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                          @PathVariable Long roomId) {
        Long memberid = userDetails.getMember().getMemberNo();
        chatRoomService.exitRoom(roomId, memberid);
        return ResponseEntity.ok().body(OkDto.valueOf("true"));
    }


}
