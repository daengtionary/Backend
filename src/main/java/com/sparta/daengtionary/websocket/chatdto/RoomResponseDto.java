package com.sparta.daengtionary.websocket.chatdto;


import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static com.sparta.daengtionary.websocket.chatroom.ChatRoomService.MemberTypeEnum.Type.ACCEPTOR;
import static com.sparta.daengtionary.websocket.chatroom.ChatRoomService.MemberTypeEnum.Type.REQUESTER;

@Getter
@NoArgsConstructor
public class RoomResponseDto {

    private Long roomId;
    private Long memberId;
    private String nickname;
    private String senderName;
    private String message;
    private LocalDateTime date;
    private Boolean isRead;
    private Boolean isBanned;
    private int unreadCnt;
    private String type;

    public static RoomResponseDto createOf(String type, String flag, RoomDto dto, int unreadCnt, Boolean isBanned){

        RoomResponseDto responseDto = new RoomResponseDto();

        responseDto.roomId = dto.getRoomId();
        responseDto.nickname = dto.getAccNickname();
        responseDto.message = dto.getMessage();
        responseDto.date = dto.getDate();
        responseDto.isRead = dto.getIsRead();
        responseDto.isBanned = isBanned;
        responseDto.unreadCnt = unreadCnt;
        responseDto.senderName = String.valueOf(dto.getAccId());
        responseDto.type = type;

        switch ( flag ) {

            case ACCEPTOR:

                responseDto.memberId = dto.getReqId();
                responseDto.nickname = dto.getReqNickname();
                break;

            case REQUESTER:

                responseDto.memberId = dto.getAccId();
                responseDto.nickname = dto.getAccNickname();
                break;

            default: break;
        }

        return responseDto;
    }


}