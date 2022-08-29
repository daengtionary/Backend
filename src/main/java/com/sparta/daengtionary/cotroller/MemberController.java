package com.sparta.daengtionary.cotroller;

import com.sparta.daengtionary.dto.request.MemberRequestDto;
import com.sparta.daengtionary.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<?> createMember(@RequestBody MemberRequestDto requestDto) {
        return memberService.createMember(requestDto);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginMember(@RequestBody MemberRequestDto requestDto,
                                         HttpServletResponse response) {
        return memberService.loginMember(requestDto, response);
    }

}