package com.sparta.daengtionary.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.daengtionary.domain.Member;
import com.sparta.daengtionary.domain.UserDetailsImpl;
import com.sparta.daengtionary.dto.request.KakaoUserInfoDto;
import com.sparta.daengtionary.dto.request.MemberRequestDto;
import com.sparta.daengtionary.dto.request.TokenDto;
import com.sparta.daengtionary.dto.response.MemberResponseDto;
import com.sparta.daengtionary.dto.response.ResponseBodyDto;
import com.sparta.daengtionary.jwt.TokenProvider;
import com.sparta.daengtionary.repository.MemberRepository;
import com.sparta.daengtionary.util.Authority;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final ResponseBodyDto responseBodyDto;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;
    @Value("${KakaoRestApiKey}")
    private String kakaoRestApiKey;

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

        Member member = getMemberByEmail(login.getEmail());

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

    //카카오 로그인
    public ResponseEntity<?> kakaoLogin(String code,
                                        HttpServletResponse response) throws JsonProcessingException {

        // 1. "인가 코드"로 "액세스 토큰" 요청
        String accessToken = getAccessToken(code);

        // 2. 토큰으로 카카오 API 호출
        KakaoUserInfoDto kakaoUserInfoDto = getKakaoUserInfo(accessToken);

        // 3. 가입 여부 확인 후 비회원일 경우 회원가입
        Member kakaoUser = getMemberByKakaoId(kakaoUserInfoDto.getKakaoId());

        if (kakaoUser == null) {
            kakaoUser = Member.builder()
                    .email(kakaoUserInfoDto.getEmail())
                    .password(passwordEncoder.encode(UUID.randomUUID().toString()))
                    .nickname(kakaoUserInfoDto.getNickname())
                    .role(Authority.USER)
                    .build();

            memberRepository.save(kakaoUser);
        }

        // 4. 강제 kakao로그인 처리
        UserDetails userDetails = new UserDetailsImpl(kakaoUser);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        TokenDto tokenDto = tokenProvider.generateToken(authentication);
        tokenToHeaders(tokenDto, response);

        return responseBodyDto.success(MemberResponseDto.builder().build(), kakaoUser.getKakaoId() + "님 환영합니다 :)", HttpStatus.OK);

    }


    private String getAccessToken(String code) throws JsonProcessingException {

        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP Body 생성
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", kakaoRestApiKey);
        body.add("redirect_uri", "http://localhost:3000/kakao/callback");
        body.add("code", code);

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(body, headers);

        RestTemplate rt = new RestTemplate();

        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        // HTTP 응답 (JSON) -> 액세스 토큰 파싱
        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        return jsonNode.get("access_token").asText();
    }

    private KakaoUserInfoDto getKakaoUserInfo(String accessToken) throws JsonProcessingException {

        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();

        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoUserInfoRequest = new HttpEntity<>(headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoUserInfoRequest,
                String.class
        );

        String responseBody = response.getBody();

        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode jsonNode = objectMapper.readTree(responseBody);

        Long id = jsonNode.get("id").asLong();
        String nickname = jsonNode.get("properties").get("nickname").asText();
        String email = jsonNode.get("kakao_account").get("email").asText();

        return KakaoUserInfoDto.builder()
                .kakaoId(id)
                .email(email)
                .nickname(nickname)
                .build();
    }


    @Transactional(readOnly = true)
    public Member getMemberByEmail(String email) {
        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        return optionalMember.orElse(null);
    }

    @Transactional(readOnly = true)
    public Member getMemberByKakaoId(Long kakaoId) {
        Optional<Member> optionalMember = memberRepository.findByKakaoId(kakaoId);
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