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
    private final ResponseBodyDto responseBodyDto;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;

    @Transactional
    public ResponseEntity<?> signup(MemberRequestDto.Signup signup) {

        if (signup.getRole().ordinal() == 1) {

            String adminCode = "daeng0829";

            if (!signup.getAdminCode().equals(adminCode)) {
                return responseBodyDto.fail("관리자 코드가 일치하지 않습니다.", HttpStatus.BAD_REQUEST);
            }

            return saveMember(signup);

        }

        return saveMember(signup);

    }

    @Transactional
    public ResponseEntity<?> login(MemberRequestDto.Login login,
                                   HttpServletResponse response) {

        Member member = getMemberEmail(login.getEmail());

        if (member == null) {
            return responseBodyDto.fail("존재하지 않는 email 입니다.", HttpStatus.BAD_REQUEST);
        }

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        TokenDto tokenDto = tokenProvider.generateToken(authentication);

        tokenToHeaders(tokenDto, response);

        return responseBodyDto.success(MemberResponseDto.builder()
                .memberNo(member.getMemberNo())
                .role(member.getRole())
                .email(member.getEmail())
                .name(member.getName())
                .nickname(member.getNickname())
                .phoneNumber(member.getPhoneNumber())
                .createdAt(member.getCreatedAt())
                .modifiedAt(member.getModifiedAt())
                .build(),
                member.getNickname() + "님 환영합니다 :)",
                HttpStatus.OK
        );

    }


    @Transactional(readOnly = true)
    public Member getMemberEmail(String email) {
        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        return optionalMember.orElse(null);
    }

    public ResponseEntity<?> checkEmail(String email) {

        if (memberRepository.existsByEmail(email)) {
            return responseBodyDto.fail("중복된 email 입니다.", HttpStatus.BAD_REQUEST);
        }

        return responseBodyDto.success("사용 가능한 email 입니다.");

    }

    public ResponseEntity<?> checkNick(String nick) {

        if (memberRepository.existsByNickname(nick)) {
            return responseBodyDto.fail("중복된 닉네임 입니다.", HttpStatus.BAD_REQUEST);
        }

        return responseBodyDto.success("사용 가능한 닉네임 입니다.");

    }

    private ResponseEntity<?> saveMember(MemberRequestDto.Signup signup) {

        if (memberRepository.existsByEmail(signup.getEmail())) {
            return responseBodyDto.fail("중복된 email 입니다.", HttpStatus.BAD_REQUEST);
        }

        if (memberRepository.existsByNickname(signup.getNickname())) {
            return responseBodyDto.fail("중복된 닉네임 입니다.", HttpStatus.BAD_REQUEST);
        }

        Member member = Member.builder()
                .email(signup.getEmail())
                .password(passwordEncoder.encode(signup.getPassword()))
                .name(signup.getName())
                .nickname(signup.getNickname())
                .phoneNumber(signup.getPhoneNumber())
                .role(signup.getRole())
                .build();

        memberRepository.save(member);

        return responseBodyDto.success(
                MemberResponseDto.builder()
                        .memberNo(member.getMemberNo())
                        .role(member.getRole())
                        .email(member.getEmail())
                        .name(member.getName())
                        .nickname(member.getNickname())
                        .phoneNumber(member.getPhoneNumber())
                        .createdAt(member.getCreatedAt())
                        .modifiedAt(member.getModifiedAt())
                        .build(),
                member.getNickname() + "님 가입을 축하힙니다 :)",
                HttpStatus.OK
        );

    }

    public void tokenToHeaders(TokenDto tokenDto, HttpServletResponse response) {
        response.addHeader("Refresh-Token", tokenDto.getRefreshToken());
        response.addHeader("Authorization", "Bearer " + tokenDto.getAccessToken());
        response.addHeader("Access-Token-Expire-Time", tokenDto.getAccessTokenExpiresIn().toString());
    }

}