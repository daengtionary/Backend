package com.sparta.daengtionary.cotroller;

import com.sparta.daengtionary.dto.request.MapPutRequestDto;
import com.sparta.daengtionary.dto.request.MapRequestDto;
import com.sparta.daengtionary.service.MapService;
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

    @PostMapping("/create")
    public ResponseEntity<?> createRoom(@RequestPart(value = "data") MapRequestDto mapRequestDto,
                                       @RequestPart(value = "imgUrl", required = false) List<MultipartFile> mapImgs) {
        return mapService.createMap(mapRequestDto, mapImgs);
    }


    @PostMapping
    public ResponseEntity<?> getAllRoomCategory(@RequestParam String orderby,
                                               Pageable pageable ) {
        String category = "shop";
        return mapService.getAllMapByCategory(category, orderby, pageable);
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
}
