package com.sparta.daengtionary.service;

import com.sparta.daengtionary.domain.Member;
import com.sparta.daengtionary.dto.request.MemberRequestDto;
import com.sparta.daengtionary.dto.request.TokenDto;
import com.sparta.daengtionary.dto.response.ResponseBodyDto;
import com.sparta.daengtionary.dto.response.MemberResponseDto;
import com.sparta.daengtionary.jwt.TokenProvider;
import com.sparta.daengtionary.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;

    @Transactional
    public ResponseEntity<?> createMember(MemberRequestDto requestDto) {

        Member member = Member.builder()
                .email(requestDto.getEmail())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .name(requestDto.getName())
                .nickname(requestDto.getNickname())
                .phoneNumber(requestDto.getPhoneNumber())
                .role(requestDto.getRole())
                .build();

        memberRepository.save(member);

        return new ResponseEntity<>(ResponseBodyDto.success(MemberResponseDto.builder()
                .memberNo(member.getMemberNo())
                .role(member.getRole())
                .email(member.getEmail())
                .name(member.getName())
                .nickname(member.getNickname())
                .phoneNumber(member.getPhoneNumber())
                .createdAt(member.getCreatedAt())
                .modifiedAt(member.getModifiedAt())
                .build()),
                HttpStatus.OK
        );

    }

    @Transactional
    public ResponseEntity<?> loginMember(MemberRequestDto requestDto,
                                         HttpServletResponse response) {

        Member member = findEmail(requestDto.getEmail());

        if (member == null) {
            return new ResponseEntity<>(ResponseBodyDto.fail("EMAIL_NOT_FOUND", "존재하지 않는 email 입니다."), HttpStatus.OK);
        }

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(requestDto.getEmail(), requestDto.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);
        tokenToHeaders(tokenDto, response);

        return new ResponseEntity<>(ResponseBodyDto.success(MemberResponseDto.builder()
                .memberNo(member.getMemberNo())
                .role(member.getRole())
                .email(member.getEmail())
                .name(member.getName())
                .nickname(member.getNickname())
                .phoneNumber(member.getPhoneNumber())
                .createdAt(member.getCreatedAt())
                .modifiedAt(member.getModifiedAt())
                .build()),
                HttpStatus.OK
        );

    }


    @Transactional(readOnly = true)
    public Member findEmail(String email) {
        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        return optionalMember.orElse(null);
    }

    @Transactional(readOnly = true)
    public Member findNickname(String nick) {
        Optional<Member> optionalMember = memberRepository.findByNickname(nick);
        return optionalMember.orElse(null);
    }

    public void tokenToHeaders(TokenDto tokenDto, HttpServletResponse response) {
        response.addHeader("Authorization", "Bearer " + tokenDto.getAccessToken());
        response.addHeader("Access-Token-Expire-Time", tokenDto.getAccessTokenExpiresIn().toString());
    }
}