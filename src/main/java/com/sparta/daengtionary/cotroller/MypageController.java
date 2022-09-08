package com.sparta.daengtionary.cotroller;

import com.sparta.daengtionary.dto.request.DogRequestDto;
import com.sparta.daengtionary.dto.request.MemberRequestDto;
import com.sparta.daengtionary.service.MypageService;
import com.sparta.daengtionary.service.TradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MypageController {
    private final MypageService mypageService;
    private final TradeService tradeService;

    @GetMapping("/info")
    public ResponseEntity<?> getMemberInfo() {
        return mypageService.getMemberInfo();
    }

    @GetMapping("/trade/mypost")
    public ResponseEntity<?> getTradeByMyPost() {
        return tradeService.getTradeByMyPost();
    }

    @PatchMapping("/nick")
    public ResponseEntity<?> updateByNick(@RequestBody MemberRequestDto.Update update) {
        return mypageService.updateByNick(update);
    }

    @PostMapping("/dogs")
    public ResponseEntity<?> createDogProfile(@RequestPart(value = "data") DogRequestDto requestDto,
                                              @RequestPart(value = "image", required = false) MultipartFile multipartFile) {
        return mypageService.createDogProfile(requestDto, multipartFile);
    }

    @PatchMapping("/dogs/{dogNo}")
    public ResponseEntity<?> updateByDogProfile(@PathVariable Long dogNo,
                                                @RequestPart(value = "data") DogRequestDto requestDto,
                                                @RequestPart(value = "image", required = false) MultipartFile multipartFile) {
        return mypageService.updateByDogProfile(dogNo, requestDto, multipartFile);
    }

    @PatchMapping("/dogs/image/{dogNo}")
    public ResponseEntity<?> updateByDogImage(@PathVariable Long dogNo,
                                              @RequestPart MultipartFile multipartFile) {
        return mypageService.updateByDogImage(dogNo, multipartFile);
    }

    @DeleteMapping("/dogs/{dogNo}")
    public ResponseEntity<?> deleteByDogProfile(@PathVariable Long dogNo) {
        return mypageService.deleteByDogProfile(dogNo);
    }

    @DeleteMapping("/dogs/image/{dogNo}")
    public ResponseEntity<?> deleteByDogImage(@PathVariable Long dogNo) {
        return mypageService.deleteByDogImage(dogNo);
    }
}