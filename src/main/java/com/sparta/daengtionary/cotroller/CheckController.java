package com.sparta.daengtionary.cotroller;

import com.sparta.daengtionary.service.CheckService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/check")
@RequiredArgsConstructor
public class CheckController {

    private final CheckService checkService;

    @GetMapping("/email")
    public ResponseEntity<?> checkEmail(@RequestParam String email) {
        return checkService.checkEmail(email);
    }

    @GetMapping("/nick")
    public ResponseEntity<?> checkNickname(@RequestParam String nick) {
        return checkService.checkNickname(nick);
    }

}