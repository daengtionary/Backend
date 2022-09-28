package com.sparta.daengtionary.category.member.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparta.daengtionary.aop.util.Timestamped;
import com.sparta.daengtionary.category.member.util.Authority;
import com.sparta.daengtionary.category.mypage.domain.Dog;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class Member extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberNo;
    @Column(nullable = false)
    private String email;
    @JsonIgnore
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String nick;
    @Column(nullable = false)
    private Authority role;
    @Column
    private Long kakaoId;
    @OneToMany(mappedBy = "member")
    private List<Dog> dogs;

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

    public boolean validatePassword(PasswordEncoder passwordEncoder, String password) {
        return passwordEncoder.matches(password, this.password);
    }

    public void update(String nick) {
        this.nick = nick;
    }
}