package com.sparta.daengtionary.cotroller;


import com.sparta.daengtionary.configration.error.CustomException;
import com.sparta.daengtionary.configration.error.ErrorCode;
import com.sparta.daengtionary.dto.request.MapRequestDto;
import com.sparta.daengtionary.service.AwsS3UploadService;
import com.sparta.daengtionary.service.MapService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MapController {

    private final MapService mapService;
    private final AwsS3UploadService s3UploadService;

    @PostMapping("/hospital")
    public ResponseEntity<?> createMap(@RequestPart(value = "content") MapRequestDto mapRequestDto,
                                       @RequestPart(value = "imgUrl", required = false) List<MultipartFile> mapImgs) {
        if(mapImgs == null){
            throw new CustomException(ErrorCode.WRONG_INPUT_CONTENT);
        }
        List<String> imgPaths = s3UploadService.upload(mapImgs);
        System.out.println("IMG 경로 : "+ imgPaths);
        return mapService.createMap(mapRequestDto,imgPaths);
    }

}
