package com.sparta.daengtionary.category.member.dto.request;

import com.sparta.daengtionary.category.member.util.Authority;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class MemberRequestDto {

    @Getter
    @Setter
    public static class Signup {
        private String email;
        private String password;
        private String nick;
        private Authority role;
        private String adminCode;
    }

    @Getter
    @Setter
    public static class Login {
        private String email;
        private String password;
    }

    @Getter
    @Setter
    public static class Update {
        private String nick;
    }
}