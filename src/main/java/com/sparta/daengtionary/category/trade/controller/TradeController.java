package com.sparta.daengtionary.category.trade.controller;

import com.sparta.daengtionary.category.trade.dto.request.TradeRequestDto;
import com.sparta.daengtionary.category.trade.service.TradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/trade")
@RequiredArgsConstructor
public class TradeController {

    private final TradeService tradeService;

    @PostMapping("/create")
    public ResponseEntity<?> createTrade(@RequestPart(value = "data") TradeRequestDto requestDto,
                                         @RequestPart(value = "imgUrl", required = false) List<MultipartFile> multipartFiles) {
        return tradeService.createTrade(requestDto, multipartFiles);
    }

    @GetMapping()
    public ResponseEntity<?> getTradeSort(@RequestParam String sort, @RequestParam int pagenum, @RequestParam int pagesize) {
        return tradeService.getTradeSort(sort, pagenum, pagesize);
    }

    @GetMapping("/search")
    public ResponseEntity<?> getSearchTrade(@RequestParam String title, @RequestParam String content, @RequestParam String nick,
                                            @RequestParam String address, @RequestParam String postStatus, @RequestParam String sort,
                                            @RequestParam int pagenum, @RequestParam int pagesize) {
        return tradeService.getSearchTrade(title, content, nick, address, postStatus, sort, pagenum, pagesize);
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


}
