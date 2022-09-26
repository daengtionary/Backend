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
@RequestMapping("/room")
@RequiredArgsConstructor
public class RoomController {

    private final MapService mapService;
    private final MapReviewService mapReviewService;

    @PostMapping("/create")
    public ResponseEntity<?> createRoom(@RequestPart(value = "data") MapRequestDto mapRequestDto,
                                        @RequestPart(value = "imgUrl", required = false) List<MultipartFile> mapImgs) {
        return mapService.createMap(mapRequestDto, mapImgs);
    }


    @GetMapping()
    public ResponseEntity<?> getAllRoomCategory(@RequestParam String address,
                                                @RequestParam int pageNum, @RequestParam int pageSize) {
        String category = "room";
        return mapService.getAllMapByCategory(category, address, pageNum, pageSize);
    }

    @GetMapping("/search")
    public ResponseEntity<?> getSearchMap(@RequestParam String title, @RequestParam String content, @RequestParam String nick,
                                          @RequestParam String address, @RequestParam int pageNum, @RequestParam int pageSize) {
        return mapService.getSearchMap("room", title, content, nick, address, pageNum, pageSize);
    }

    @GetMapping("/{roomNo}")
    public ResponseEntity<?> getRoom(@PathVariable Long roomNo) {
        mapService.mapViewUpdate(roomNo);
        return mapService.getAllMap(roomNo);
    }

    @PatchMapping("/{roomNo}")
    public ResponseEntity<?> updateRoom(@PathVariable Long roomNo, @RequestPart(value = "data") MapPutRequestDto requestDto,
                                        @RequestPart(value = "imgUrl", required = false) List<MultipartFile> multipartFiles) {
        return mapService.mapUpdate(requestDto, roomNo, multipartFiles);
    }

    @DeleteMapping("/{roomNo}")
    public ResponseEntity<?> deleteRoom(@PathVariable Long roomNo) {
        return mapService.mapDelete(roomNo);
    }

    @PostMapping("/review/create/{roomNo}")
    public ResponseEntity<?> createReview(@RequestPart(value = "data") ReviewRequestDto requestDto, @PathVariable Long roomNo) {
        return mapReviewService.createMapReview(roomNo, requestDto);
    }

    @PatchMapping("/review/{roomNo}/{reviewNo}")
    public ResponseEntity<?> updateRoomReview(@RequestPart(value = "data") ReviewRequestDto requestDto, @PathVariable Long roomNo,
                                              @PathVariable Long reviewNo) {
        return mapReviewService.updateMapReview(roomNo, reviewNo, requestDto);
    }

    @DeleteMapping("/review/{roomNo}/{reviewNo}")
    public ResponseEntity<?> deleteRoomReview(@PathVariable Long roomNo, @PathVariable Long reviewNo) {
        return mapReviewService.deleteMapReview(roomNo, reviewNo);
    }
}
