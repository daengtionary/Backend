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
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String nickname;
    @Column(nullable = false)
    private String phoneNumber;
    @Column(nullable = false)
    private Authority role;


    public Member() {
    }

    @Builder
    public Member(Long memberNo, String email, String password, String name, String nickname,
                  String phoneNumber, Authority role) {

        this.memberNo = memberNo;
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.role = role;

    }

}