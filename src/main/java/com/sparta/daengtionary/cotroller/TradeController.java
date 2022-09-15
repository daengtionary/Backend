package com.sparta.daengtionary.cotroller;

import com.sparta.daengtionary.dto.request.ReviewRequestDto;
import com.sparta.daengtionary.dto.request.TradeRequestDto;
import com.sparta.daengtionary.service.TradeReviewService;
import com.sparta.daengtionary.service.TradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/trade")
@RequiredArgsConstructor
public class TradeController {

    private final TradeService tradeService;
    private final TradeReviewService tradeReviewService;

    @PostMapping("/create")
    public ResponseEntity<?> createTrade(@RequestPart(value = "data") TradeRequestDto requestDto,
                                         @RequestPart(value = "imgUrl", required = false) List<MultipartFile> multipartFiles) {
        return tradeService.createTrade(requestDto, multipartFiles);
    }

    @GetMapping()
    public ResponseEntity<?> getTradeSort(@RequestParam String direction, Pageable pageable) {
        return tradeService.getTradeSort(direction, pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<?> getSearchCommunity(@RequestParam String title, @RequestParam String content, @RequestParam String nick,
                                                @RequestParam String status, @RequestParam String category, @RequestParam int minPrice,
                                                @RequestParam int maxPrice, @RequestParam String direction, Pageable pageable) {
        return tradeService.getSearchTrade(title, content, nick, status, category, direction, minPrice, maxPrice, pageable);
    }


    @GetMapping("/{tradeNo}")
    public ResponseEntity<?> getTrade(@PathVariable Long tradeNo) {
        tradeService.tradeViewUpdate(tradeNo);
        return tradeService.getTrade(tradeNo);
    }

    @PatchMapping("/{tradeNo}")
    public ResponseEntity<?> updateTrade(@PathVariable Long tradeNo, @RequestPart(value = "data") TradeRequestDto requestDto,
                                         @RequestPart(value = "imgUrl", required = false) List<MultipartFile> multipartFiles) {
        return tradeService.tradeUpdate(requestDto, tradeNo, multipartFiles);
    }

    @DeleteMapping("/{tradeNo}")
    public ResponseEntity<?> deleteTrade(@PathVariable Long tradeNo) {
        return tradeService.tradeDelete(tradeNo);
    }

    @PostMapping("/review/create/{tradeNo}")
    public ResponseEntity<?> createReview(@RequestPart(value = "data") ReviewRequestDto requestDto,
                                          @RequestPart(value = "imgUrl", required = false) MultipartFile multipartFile, @PathVariable Long tradeNo) {
        return tradeReviewService.createTradeReview(tradeNo, requestDto, multipartFile);
    }

    @PatchMapping("/review/{tradeNo}/{reviewNo}")
    public ResponseEntity<?> updateRoomReview(@RequestPart(value = "data") ReviewRequestDto requestDto,
                                              @RequestPart(value = "imgUrl", required = false) MultipartFile multipartFile, @PathVariable Long tradeNo,
                                              @PathVariable Long reviewNo) {
        return tradeReviewService.updateTradeReview(tradeNo, reviewNo, requestDto, multipartFile);
    }

    @DeleteMapping("/review/{tradeNo}/{reviewNo}")
    public ResponseEntity<?> deleteRoomReview(@PathVariable Long tradeNo, @PathVariable Long reviewNo) {
        return tradeReviewService.deleteTradeReview(tradeNo, reviewNo);
    }

}
