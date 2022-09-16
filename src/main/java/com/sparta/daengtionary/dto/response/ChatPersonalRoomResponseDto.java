package com.sparta.daengtionary.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sparta.daengtionary.domain.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChatPersonalRoomResponseDto {
    private Long chatNo;
    private String title;
    private Member creator;
    private Member target;


    @Builder
    public ChatPersonalRoomResponseDto(Long chatNo, String title, Member creator, Member target) {
        this.chatNo = chatNo;
        this.title = title;
        this.creator = creator;
        this.target = target;
    }
}