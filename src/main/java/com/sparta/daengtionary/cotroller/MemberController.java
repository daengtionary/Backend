package com.sparta.daengtionary.cotroller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.daengtionary.dto.request.MemberRequestDto;
import com.sparta.daengtionary.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/checkemail")
    public ResponseEntity<?> checkDuplicateByEmail(@RequestParam String email) {
        return memberService.checkDuplicateByEmail(email);
    }

    @GetMapping("/checknick")
    public ResponseEntity<?> checkDuplicateByNick(@RequestParam String nick) {
        return memberService.checkDuplicateByNick(nick);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody MemberRequestDto.Signup signup) {
        return memberService.signup(signup);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody MemberRequestDto.Login login,
                                   HttpServletResponse response) {
        return memberService.login(login, response);
    }

    @GetMapping("/kakao")
    public ResponseEntity<?> kakaoLogin(@RequestParam String code,
                                        HttpServletResponse response)  throws JsonProcessingException {
        return memberService.kakaoLogin(code, response);
    }
}