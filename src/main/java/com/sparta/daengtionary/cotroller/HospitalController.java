package com.sparta.daengtionary.cotroller;


import com.sparta.daengtionary.dto.request.MapPutRequestDto;
import com.sparta.daengtionary.dto.request.MapRequestDto;
import com.sparta.daengtionary.dto.request.ReviewRequestDto;
import com.sparta.daengtionary.service.MapReviewService;
import com.sparta.daengtionary.service.MapService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@RestController
@RequestMapping("/hospital")
@RequiredArgsConstructor
public class HospitalController {

    private final MapService mapService;
    private final MapReviewService mapReviewService;

    @PostMapping("/create")
    public ResponseEntity<?> createRoom(@RequestPart(value = "data") MapRequestDto mapRequestDto,
                                       @RequestPart(value = "imgUrl", required = false) List<MultipartFile> mapImgs) {
        return mapService.createMap(mapRequestDto, mapImgs);
    }

    @GetMapping()
    public ResponseEntity<?> getAllRoomCategory(@RequestParam String address, @RequestParam String direction,
                                               Pageable pageable ) {
        
        String category = "hospital";
        return mapService.getAllMapByCategory(category,direction,address ,pageable);
    }

    @GetMapping("/{mapNo}")
    public ResponseEntity<?> getRoom(@PathVariable Long mapNo) {
        mapService.mapViewUpdate(mapNo);
        return mapService.getAllMap(mapNo);
    }

    @PatchMapping("/{mapNo}")
    public ResponseEntity<?> updateRoom(@PathVariable Long mapNo, @RequestPart(value = "data") MapPutRequestDto requestDto,
                                       @RequestPart(value = "imgUrl", required = false) List<MultipartFile> multipartFiles) {
        return mapService.mapUpdate(requestDto, mapNo,multipartFiles);
    }

    @DeleteMapping("/{mapNo}")
    public ResponseEntity<?> deleteRoom(@PathVariable Long mapNo) {
        return mapService.mapDelete(mapNo);
    }

    @PostMapping("/review/create/{hospitalNo}")
    public ResponseEntity<?> createReview(@RequestPart(value = "data") ReviewRequestDto requestDto,
                                          @RequestPart(value = "imgUrl",required = false)MultipartFile multipartFile,@PathVariable Long hospitalNo){
        return mapReviewService.createMapReview(hospitalNo,requestDto,multipartFile);
    }

    @PatchMapping("/review/{hospitalNo}/{reviewNo}")
    public ResponseEntity<?> updateRoomReview(@RequestPart(value = "data") ReviewRequestDto requestDto,
                                             @RequestPart(value = "imgUrl",required = false)MultipartFile multipartFile,@PathVariable Long hospitalNo,
                                             @PathVariable Long reviewNo){
        return mapReviewService.updateMapReview(hospitalNo,reviewNo,requestDto,multipartFile);
    }

    @DeleteMapping("/review/{hospitalNo}/{reviewNo}")
    public ResponseEntity<?> deleteRoomReview(@PathVariable Long hospitalNo,@PathVariable Long reviewNo){
        return mapReviewService.deleteMapReview(hospitalNo,reviewNo);
    }
}
