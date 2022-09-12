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
    public ResponseEntity<?> getAllRoomCategory(@RequestParam String address, @RequestParam String direction,
                                               Pageable pageable ) {
        String category = "room";
        return mapService.getAllMapByCategory(category,direction,address, pageable);
    }

    @GetMapping("/{roomNo}")
    public ResponseEntity<?> getRoom(@PathVariable Long roomNo) {
        mapService.mapViewUpdate(roomNo);
        return mapService.getAllMap(roomNo);
    }

    @PatchMapping("/{roomNo}")
    public ResponseEntity<?> updateRoom(@PathVariable Long roomNo, @RequestPart(value = "data") MapPutRequestDto requestDto,
                                       @RequestPart(value = "imgUrl", required = false) List<MultipartFile> multipartFiles) {
        return mapService.mapUpdate(requestDto, roomNo,multipartFiles);
    }

    @DeleteMapping("/{roomNo}")
    public ResponseEntity<?> deleteRoom(@PathVariable Long roomNo) {
        return mapService.mapDelete(roomNo);
    }

    @PostMapping("/review/create/{roomNo}")
    public ResponseEntity<?> createReview(@RequestPart(value = "data") ReviewRequestDto requestDto,
                                          @RequestPart(value = "imgUrl",required = false)MultipartFile multipartFile,@PathVariable Long roomNo){
        return mapReviewService.createMapReview(roomNo,requestDto,multipartFile);
    }

    @PatchMapping("/review/{roomNo}/{reviewNo}")
    public ResponseEntity<?> updateRoomReview(@RequestPart(value = "data") ReviewRequestDto requestDto,
                                              @RequestPart(value = "imgUrl",required = false)MultipartFile multipartFile,@PathVariable Long roomNo,
                                              @PathVariable Long reviewNo){
        return mapReviewService.updateMapReview(roomNo,reviewNo,requestDto,multipartFile);
    }

    @DeleteMapping("/review/{roomNo}/{reviewNo}")
    public ResponseEntity<?> deleteRoomReview(@PathVariable Long roomNo,@PathVariable Long reviewNo){
        return mapReviewService.deleteMapReview(roomNo,reviewNo);
    }
}
