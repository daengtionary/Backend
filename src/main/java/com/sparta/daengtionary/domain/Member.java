package com.sparta.daengtionary.domain;

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
    @Column
    private String name;
    @Column(nullable = false)
    private String nickname;
    @Column
    private String phoneNumber;
    @Column(nullable = false)
    private Authority role;
    @Column
    private Long kakaoId;


    public Member() {
    }

    @Builder
    public Member(Long memberNo, String email, String password, String name, String nickname,
                  String phoneNumber, Authority role, Long kakaoId) {

        this.memberNo = memberNo;
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.kakaoId = kakaoId;

    }

}