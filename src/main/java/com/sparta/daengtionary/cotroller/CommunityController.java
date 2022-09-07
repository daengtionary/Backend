package com.sparta.daengtionary.cotroller;


import com.sparta.daengtionary.dto.request.CommunityRequestDto;
import com.sparta.daengtionary.service.CommunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/community")
@RequiredArgsConstructor
public class CommunityController {

    private final CommunityService service;

    @PostMapping("/create")
    public ResponseEntity<?> creatCommunity(@RequestPart(value = "data")CommunityRequestDto requestDto,
                                            @RequestPart(value = "imgUrl",required = false) List<MultipartFile> multipartFileList){
        return service.createCommunity(requestDto,multipartFileList);
    }

    @PostMapping()
    public ResponseEntity<?> getCommunitySort(@RequestParam String sort, Pageable pageable){
        return service.getCommunitySort(sort,pageable);
    }

    @GetMapping("/{comNo}")
    public ResponseEntity<?> getCommunity(@PathVariable Long comNo){
        return service.getCommunity(comNo);
    }

    @PatchMapping("/{comNo}")
    public ResponseEntity<?> updateCommunity(@PathVariable Long comNo,@RequestPart(value = "data") CommunityRequestDto requestDto,
                                             @RequestPart(value = "imgUrl", required = false) List<MultipartFile> multipartFiles){
        return service.communityUpdate(requestDto,comNo,multipartFiles);
    }

    @DeleteMapping("/{comNo}")
    public ResponseEntity<?> deleteCommunity(@PathVariable Long comNo, @PathVariable Long memberNo){
        return service.communityDelete(comNo,memberNo);
    }

}
