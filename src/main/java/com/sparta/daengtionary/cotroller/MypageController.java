package com.sparta.daengtionary.cotroller;

import com.sparta.daengtionary.dto.request.DogRequestDto;
import com.sparta.daengtionary.dto.request.MemberRequestDto;
import com.sparta.daengtionary.service.MypageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MypageController {
    private final MypageService mypageService;

    @PatchMapping("/updatemember")
    public ResponseEntity<?> updateByNick(@RequestBody MemberRequestDto.Update update,
                                          HttpServletRequest request) {
        return mypageService.updateByNick(update);
    }

    @PostMapping("/dog")
    public ResponseEntity<?> createDogProfile(@RequestBody DogRequestDto requestDto) {
        return mypageService.createDogProfile(requestDto);
    }

    @GetMapping("/memberinfo")
    public ResponseEntity<?> getMemberInfo() {
        return mypageService.getMemberInfo();
    }

    @PatchMapping("/updatedog/{dogNo}")
    public ResponseEntity<?> updateByDog(@PathVariable Long dogNo,
                                         @RequestBody DogRequestDto requestDto) {

        return mypageService.updateByDog(dogNo, requestDto);
    }
}