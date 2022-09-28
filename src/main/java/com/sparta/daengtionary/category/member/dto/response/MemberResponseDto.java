package com.sparta.daengtionary.category.member.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.sparta.daengtionary.category.member.util.Authority;
import com.sparta.daengtionary.category.mypage.dto.response.MypageResponseDto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MemberResponseDto {
    private Long memberNo;

    private MypageResponseDto mypageResponseDto;

    private Authority role;

    private String email;

    private String nick;

    @JsonFormat(pattern = "yy-MM-dd hh:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yy-MM-dd hh:mm:ss")
    private LocalDateTime modifiedAt;


    @Builder
    public MemberResponseDto(Long memberNo, Authority role, String email, String nick,
                             MypageResponseDto mypageResponseDto, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.memberNo = memberNo;
        this.mypageResponseDto = mypageResponseDto;
        this.role = role;
        this.email = email;
        this.nick = nick;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}