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

    @PatchMapping("/update")
    public ResponseEntity<?> updateByNick(@RequestBody MemberRequestDto.Update update,
                                          HttpServletRequest request) {
        return mypageService.updateByNick(update, request);
    }

    @PostMapping("/dog")
    public ResponseEntity<?> createDogProfile(@RequestBody DogRequestDto requestDto,
                                              HttpServletRequest request) {
        return mypageService.createDogProfile(requestDto, request);
    }
}
