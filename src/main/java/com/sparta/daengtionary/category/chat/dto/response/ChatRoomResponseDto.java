package com.sparta.daengtionary.category.chat.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.sparta.daengtionary.category.member.dto.response.MemberResponseDto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChatRoomResponseDto {
    private Long roomNo;

    private String type;

    private String title;

    private List<MemberResponseDto> chatRoomMembers;

    @JsonFormat(pattern = "yyyy년 MM월 dd일 E요일 a hh:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime lastDate;

    private String lastMessage;


    @Builder
    public ChatRoomResponseDto(Long roomNo, String type, String title, List<MemberResponseDto> chatRoomMembers,
                               LocalDateTime lastDate, String lastMessage) {
        this.roomNo = roomNo;
        this.type = type;
        this.title = title;
        this.chatRoomMembers = chatRoomMembers;
        this.lastDate = lastDate;
        this.lastMessage = lastMessage;
    }
}