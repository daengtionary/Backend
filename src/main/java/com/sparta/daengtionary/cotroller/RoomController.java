package com.sparta.daengtionary.cotroller;

import com.sparta.daengtionary.dto.request.MapPutRequestDto;
import com.sparta.daengtionary.dto.request.MapRequestDto;
import com.sparta.daengtionary.dto.request.PageRequest;
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
    private final PageRequest pageRequest;

    @PostMapping
    public ResponseEntity<?> createMap(@RequestPart(value = "data") MapRequestDto mapRequestDto,
                                       @RequestPart(value = "imgUrl", required = false) List<MultipartFile> mapImgs) {
        return mapService.createMap(mapRequestDto, mapImgs);
    }


    @GetMapping("/query")
    public ResponseEntity<?> getAllMapCategory(@RequestParam String category, @RequestParam String orderBy,
                                               Pageable pageable ) {

        return mapService.getAllMapByCategory(category, orderBy, pageable);
    }

    @GetMapping("/{mapNo}")
    public ResponseEntity<?> getMap(@PathVariable Long mapNo) {
        return mapService.getAllMap(mapNo);
    }

    @PatchMapping("/{mapNo}")
    public ResponseEntity<?> updateMap(@PathVariable Long mapNo, @RequestPart(value = "data") MapPutRequestDto requestDto) {
        return mapService.mapUpdate(requestDto, mapNo);
    }

    @DeleteMapping("/{mapNo}")
    public ResponseEntity<?> deleteMap(@PathVariable Long mapNo, @RequestParam Long memberNo) {
        return mapService.mapDelete(mapNo, memberNo);
    }
}
