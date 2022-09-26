package com.sparta.daengtionary.category.recommend.controller;

import com.sparta.daengtionary.category.recommend.dto.request.MapPutRequestDto;
import com.sparta.daengtionary.category.recommend.dto.request.MapRequestDto;
import com.sparta.daengtionary.category.recommend.dto.request.ReviewRequestDto;
import com.sparta.daengtionary.category.recommend.service.MapReviewService;
import com.sparta.daengtionary.category.recommend.service.MapService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/shop")
@RequiredArgsConstructor
public class ShopController {

    private final MapService mapService;
    private final MapReviewService mapReviewService;

    @PostMapping("/create")
    public ResponseEntity<?> createShop(@RequestPart(value = "data") MapRequestDto mapRequestDto,
                                        @RequestPart(value = "imgUrl", required = false) List<MultipartFile> mapImgs) {
        return mapService.createMap(mapRequestDto, mapImgs);
    }


    @GetMapping()
    public ResponseEntity<?> getAllShopCategory(@RequestParam String address, @RequestParam String sort, @RequestParam String direction,
                                                @RequestParam int pageNum, @RequestParam int pageSize) {
        String category = "shop";
        return mapService.getAllMapByCategory(category, address, sort, direction, pageNum, pageSize);
    }

    @GetMapping("/search")
    public ResponseEntity<?> getSearchMap(@RequestParam String title, @RequestParam String content, @RequestParam String nick, @RequestParam String sort, @RequestParam String direction,
                                          @RequestParam String address, @RequestParam int pageNum, @RequestParam int pageSize) {
        return mapService.getSearchMap("shop", title, content, nick, address, sort, direction, pageNum, pageSize);
    }

    @GetMapping("/{shopNo}")
    public ResponseEntity<?> getShop(@PathVariable Long shopNo) {
        mapService.mapViewUpdate(shopNo);
        return mapService.getAllMap(shopNo);
    }


    @PatchMapping("/{shopNo}")
    public ResponseEntity<?> updateShop(@PathVariable Long shopNo, @RequestPart(value = "data") MapPutRequestDto requestDto,
                                        @RequestPart(value = "imgUrl", required = false) List<MultipartFile> multipartFiles) {
        return mapService.mapUpdate(requestDto, shopNo, multipartFiles);
    }

    @DeleteMapping("/{shopNo}")
    public ResponseEntity<?> deleteShop(@PathVariable Long shopNo) {
        return mapService.mapDelete(shopNo);
    }

    @PostMapping("/review/create/{shopNo}")
    public ResponseEntity<?> createShopReview(@RequestPart(value = "data") ReviewRequestDto requestDto, @PathVariable Long shopNo) {
        return mapReviewService.createMapReview(shopNo, requestDto);
    }

    @PatchMapping("/review/{shopNo}/{reviewNo}")
    public ResponseEntity<?> updateShopReview(@RequestPart(value = "data") ReviewRequestDto requestDto, @PathVariable Long shopNo,
                                              @PathVariable Long reviewNo) {
        return mapReviewService.updateMapReview(shopNo, reviewNo, requestDto);
    }

    @DeleteMapping("/review/{shopNo}/{reviewNo}")
    public ResponseEntity<?> deleteShopReview(@PathVariable Long shopNo, @PathVariable Long reviewNo) {
        return mapReviewService.deleteMapReview(shopNo, reviewNo);
    }
}
