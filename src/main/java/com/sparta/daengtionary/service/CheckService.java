package com.sparta.daengtionary.service;

import com.sparta.daengtionary.domain.Member;
import com.sparta.daengtionary.dto.response.ResponseBodyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CheckService {

    private final MemberService memberService;

    public ResponseEntity<?> checkEmail(String email) {
        Member member = memberService.findEmail(email);

        if (member == null) {
            return new ResponseEntity<>(ResponseBodyDto.success("사용 가능합니다."), HttpStatus.OK);
        }

        return new ResponseEntity<>(ResponseBodyDto.fail("DUPLICATE", "이미 사용중입니다."), HttpStatus.OK);
    }

    public ResponseEntity<?> checkNickname(String nick) {
        Member member = memberService.findNickname(nick);

        if (member == null) {
            return new ResponseEntity<>(ResponseBodyDto.success("사용 가능합니다."), HttpStatus.OK);
        }

        return new ResponseEntity<>(ResponseBodyDto.fail("DUPLICATE_EMAIL", "이미 사용중입니다."), HttpStatus.OK);
    }

}