package com.sparta.daengtionary.cotroller;


import com.sparta.daengtionary.configration.error.CustomException;
import com.sparta.daengtionary.configration.error.ErrorCode;
import com.sparta.daengtionary.dto.request.MapRequestDto;
import com.sparta.daengtionary.dto.request.PageRequest;
import com.sparta.daengtionary.service.AwsS3UploadService;
import com.sparta.daengtionary.service.MapService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MapController {

    private final MapService mapService;
    private final AwsS3UploadService s3UploadService;

    private final PageRequest pageRequest;

    @PostMapping("/create/category")
    public ResponseEntity<?> createMap(@RequestPart(value = "data") MapRequestDto mapRequestDto,
                                       @RequestPart(value = "imgUrl", required = false) List<MultipartFile> mapImgs) {
        if (mapImgs == null) {
            throw new CustomException(ErrorCode.WRONG_INPUT_CONTENT);
        }
        List<String> imgPaths = s3UploadService.upload(mapImgs);
        System.out.println("IMG 경로 : " + imgPaths);
        return mapService.createMap(mapRequestDto, imgPaths);
    }
    
//    @GetMapping("/hospital/{category}&{page}&{size}")
//    @GetMapping("/hospital?type={category}&pnum={page}&snum{size}")
//    public ResponseEntity<?> getAllMapCategory(@PathVariable String category,@PathVariable int page,@PathVariable int size){
//        Pageable pageable = pageRequest.of(page, size);
//
//        return mapService.getAllMap(category, pageable);
//    }

        @GetMapping("/hospital/{category}&{orderBy}&{page}&{size}")
    public ResponseEntity<?> getAllMapCategory(@PathVariable String category,@PathVariable String orderBy,@PathVariable int page,@PathVariable int size){
        Pageable pageable = pageRequest.of(page, size);

        return mapService.getAllMap(category,orderBy, pageable);
    }

}
