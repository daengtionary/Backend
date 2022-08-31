package com.sparta.daengtionary.cotroller;

import com.sparta.daengtionary.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/check")
@RequiredArgsConstructor
public class CheckController {

    private final MemberService memberService;

    @GetMapping("/email")
    public ResponseEntity<?> checkEmail(@RequestParam String email) {
        return memberService.checkEmail(email);
    }

    @GetMapping("/nick")
    public ResponseEntity<?> checkNickname(@RequestParam String nick) {
        return memberService.checkNick(nick);
    }

}