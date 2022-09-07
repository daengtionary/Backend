package com.sparta.daengtionary.cotroller;

import com.sparta.daengtionary.dto.request.TradeRequestDto;
import com.sparta.daengtionary.service.TradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("trade")
@RequiredArgsConstructor
public class TradeController {

    private final TradeService tradeService;

    @PostMapping("/create")
    public ResponseEntity<?> createTrade(@RequestPart(value = "data")TradeRequestDto requestDto,
                                         @RequestPart(value = "imgUrl",required = false)List<MultipartFile> multipartFiles){
        return tradeService.createTrade(requestDto,multipartFiles);
    }
    @PostMapping()
    public ResponseEntity<?> getTradeSort(@RequestPart String sort, Pageable pageable){
        return tradeService.getTradeSort(sort,pageable);
    }

    @GetMapping("/{tradeNo}")
    public ResponseEntity<?> getTrade(@PathVariable Long tradeNo){
        return tradeService.getTrade(tradeNo);
    }

    @PatchMapping("/{tradeNo}")
    public ResponseEntity<?> updateTrade(@PathVariable Long tradeNo,@RequestPart(value = "data") TradeRequestDto requestDto,
                                         @RequestPart(value = "imgUrl",required = false) List<MultipartFile> multipartFiles){
        return tradeService.tradeUpdate(requestDto,tradeNo,multipartFiles);
    }
    @DeleteMapping("/{tradeNo}")
    public ResponseEntity<?> deleteTrade(@PathVariable Long tradeNo){
        return tradeService.tradeDelete(tradeNo);
    }

}
