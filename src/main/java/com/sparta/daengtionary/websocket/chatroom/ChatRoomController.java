package com.sparta.daengtionary.websocket.chatroom;



import com.sparta.daengtionary.domain.UserDetailsImpl;
import com.sparta.daengtionary.dto.request.MemberRequestDto;
import com.sparta.daengtionary.websocket.chat.ChatMessageService;
import com.sparta.daengtionary.websocket.chatdto.MessageResponseDto;
import com.sparta.daengtionary.websocket.chatdto.OkDto;
import com.sparta.daengtionary.websocket.chatdto.RoomResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomService roomService;
    private final ChatRoomRepository roomRepository;
    private final ChatMessageService messageService;


    // 채팅방 만들기
    @PostMapping("/room")
    public Long createRoom(@AuthenticationPrincipal UserDetailsImpl userDetails,
                           @RequestBody MemberRequestDto.MakeChat requestDto) {

        Long acceptorId = requestDto.getMemberid();
        Long memberid = userDetails.getMemberId();
        System.out.println("------ 주는 쪽 memberId = " + memberid);
        System.out.println("------받는쪽  acceptorId = " + acceptorId);

        return roomService.createRoom(memberid, acceptorId);
    }

    // 전체 채팅방 목록 가져오기
    @GetMapping("/rooms")
    public List<RoomResponseDto> getRooms(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long memberId = userDetails.getMemberId();
        String nickname = userDetails.getUsername();
        System.out.println(" 전체 채팅방 목록 가져오기 memberId = " + memberId);
        System.out.println(" 전체 채팅방 목록 가져오기 nickname = " + nickname);

        return roomService.getRooms(memberId , nickname);
    }

    // 개별 채팅방 메시지 불러오기
    @GetMapping("/room/{roomId}")
    public List<MessageResponseDto> getMessage(@PathVariable Long roomId ,
                                               @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long memberid = userDetails.getMemberId();
        String nickname = userDetails.getNickname();
        return messageService.getMessage(roomId, memberid, nickname);
    }


    // 채팅방 나가기
    @GetMapping("/room/exit/{roomId}")
    public ResponseEntity<OkDto> exitRoom(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                          @PathVariable Long roomId) {
        Long memberid = userDetails.getMember().getMemberNo();
        roomService.exitRoom(roomId, memberid);
        return ResponseEntity.ok().body(OkDto.valueOf("true"));
    }




}
