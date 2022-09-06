package com.sparta.daengtionary.cotroller;

import com.sparta.daengtionary.dto.request.DogRequestDto;
import com.sparta.daengtionary.dto.request.MemberRequestDto;
import com.sparta.daengtionary.service.MypageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MypageController {
    private final MypageService mypageService;

    @PatchMapping("/nick")
    public ResponseEntity<?> updateByNick(@RequestBody MemberRequestDto.Update update) {
        return mypageService.updateByNick(update);
    }

    @PostMapping("/dog")
    public ResponseEntity<?> createDogProfile(@RequestPart(value = "data") DogRequestDto requestDto,
                                              @RequestPart(value = "image", required = false) MultipartFile multipartFile) {
        return mypageService.createDogProfile(requestDto, multipartFile);
    }

    @GetMapping("/info")
    public ResponseEntity<?> getMemberInfo() {
        return mypageService.getMemberInfo();
    }

    @PatchMapping("/dog/{dogNo}")
    public ResponseEntity<?> updateByDog(@PathVariable Long dogNo,
                                         @RequestBody DogRequestDto requestDto) {

        return mypageService.updateByDog(dogNo, requestDto);
    }
}