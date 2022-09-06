package com.sparta.daengtionary.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sparta.daengtionary.util.Authority;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MemberResponseDto {

    private Long memberNo;
    private Authority role;
    private String email;
    private String nick;
    @JsonFormat(pattern = "yy-MM-dd hh:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yy-MM-dd hh:mm:ss")
    private  LocalDateTime modifiedAt;


    @Builder
    public MemberResponseDto(Long memberNo, Authority role, String email, String nick,
                             LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.memberNo = memberNo;
        this.role = role;
        this.email = email;
        this.nick = nick;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}