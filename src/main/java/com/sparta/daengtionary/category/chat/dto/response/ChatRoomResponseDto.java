package com.sparta.daengtionary.category.chat.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sparta.daengtionary.category.member.domain.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChatRoomResponseDto {
    private Long roomNo;
    private String type;
    private String title;
    private Member creator;
    private Member target;


    @Builder
    public ChatRoomResponseDto(Long roomNo, String type, String title, Member creator, Member target) {
        this.roomNo = roomNo;
        this.type = type;
        this.title = title;
        this.creator = creator;
        this.target = target;
    }
}