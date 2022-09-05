package com.sparta.daengtionary.domain;

import com.sparta.daengtionary.dto.request.MemberRequestDto;
import com.sparta.daengtionary.util.Authority;
import com.sparta.daengtionary.util.Timestamped;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Member extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberNo;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String nick;
    @Column(nullable = false)
    private Authority role;
    @Column
    private Long kakaoId;


    public Member() {
    }

    @Builder
    public Member(Long memberNo, String email, String password,
                  String nick, Authority role, Long kakaoId) {

        this.memberNo = memberNo;
        this.email = email;
        this.password = password;
        this.nick = nick;
        this.role = role;
        this.kakaoId = kakaoId;

    }

    public void update(MemberRequestDto.Update update) {
        this.nick = update.getNick();
    }

}