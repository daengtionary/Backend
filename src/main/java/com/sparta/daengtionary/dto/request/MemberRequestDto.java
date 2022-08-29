package com.sparta.daengtionary.dto.request;

import com.sparta.daengtionary.util.Authority;
import lombok.Getter;

@Getter
public class MemberRequestDto {
    private String email;
    private String password;
    private String name;
    private String nickname;
    private String phoneNumber;
    private Authority role;
}
