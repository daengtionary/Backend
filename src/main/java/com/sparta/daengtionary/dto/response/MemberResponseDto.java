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
    private String name;
    private String nickname;
    private String phoneNumber;
    @JsonFormat(pattern = "yy-MM-dd hh:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yy-MM-dd hh:mm:ss")
    private  LocalDateTime modifiedAt;


    @Builder
    public MemberResponseDto(Long memberNo, Authority role, String email, String name, String nickname,
                             String phoneNumber, LocalDateTime createdAt, LocalDateTime modifiedAt) {

        this.memberNo = memberNo;
        this.role = role;
        this.email = email;
        this.name = name;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;

    }

}