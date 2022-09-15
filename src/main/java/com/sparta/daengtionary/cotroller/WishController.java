package com.sparta.daengtionary.cotroller;

import com.sparta.daengtionary.service.WishService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class WishController {
    private final WishService wishService;

    @PostMapping("/map/{mapNo}/wish")
    public ResponseEntity<?> createWishByMap(@PathVariable Long mapNo){
        return wishService.toggleWishByMap(mapNo);
    }

    @PostMapping("/community/{comNo}/wish")
    public ResponseEntity<?> createWishByCommunity(@PathVariable Long comNo){
        return wishService.toggleWishByMap(comNo);
    }

    @PostMapping("/trade/{tradeNo}/wish")
    public ResponseEntity<?> createWishByTrade(@PathVariable Long tradeNo){
        return wishService.toggleWishByMap(tradeNo);
    }

}
