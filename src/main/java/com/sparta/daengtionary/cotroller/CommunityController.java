package com.sparta.daengtionary.cotroller;


import com.sparta.daengtionary.dto.request.CommunityRequestDto;
import com.sparta.daengtionary.dto.request.ReviewRequestDto;
import com.sparta.daengtionary.service.CommunityReviewService;
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
    private final CommunityReviewService communityReviewService;

    @PostMapping("/create")
    public ResponseEntity<?> creatCommunity(@RequestPart(value = "data") CommunityRequestDto requestDto,
                                            @RequestPart(value = "imgUrl", required = false) List<MultipartFile> multipartFileList) {
        return service.createCommunity(requestDto, multipartFileList);
    }

    @GetMapping()
    public ResponseEntity<?> getCommunitySort(@RequestParam String direction, Pageable pageable) {
        return service.getCommunitySort(direction, pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<?> getSearchCommunity(@RequestParam String title, @RequestParam String content, @RequestParam String nick,
                                                @RequestParam String direction, Pageable pageable) {
        return service.getSearchCommunity(title, content, nick, direction, pageable);
    }


    @GetMapping("/{comNo}")
    public ResponseEntity<?> getCommunity(@PathVariable Long comNo) {
        service.communityViewUpdate(comNo);
        return service.getCommunity(comNo);
    }

    @PatchMapping("/{comNo}")
    public ResponseEntity<?> updateCommunity(@PathVariable Long comNo, @RequestPart(value = "data") CommunityRequestDto requestDto,
                                             @RequestPart(value = "imgUrl", required = false) List<MultipartFile> multipartFiles) {
        return service.communityUpdate(requestDto, comNo, multipartFiles);
    }

    @DeleteMapping("/{comNo}")
    public ResponseEntity<?> deleteCommunity(@PathVariable Long comNo) {
        return service.communityDelete(comNo);
    }

    @PostMapping("/review/create/{comNo}")
    public ResponseEntity<?> createReview(@RequestPart(value = "data") ReviewRequestDto requestDto,
                                          @RequestPart(value = "imgUrl", required = false) MultipartFile multipartFile, @PathVariable Long comNo) {
        return communityReviewService.createCommunityReview(comNo, requestDto, multipartFile);
    }

    @PatchMapping("/review/{comNo}/{reviewNo}")
    public ResponseEntity<?> updateRoomReview(@RequestPart(value = "data") ReviewRequestDto requestDto,
                                              @RequestPart(value = "imgUrl", required = false) MultipartFile multipartFile, @PathVariable Long comNo,
                                              @PathVariable Long reviewNo) {
        return communityReviewService.updateCommunityReview(comNo, reviewNo, requestDto, multipartFile);
    }

    @DeleteMapping("/review/{comNo}/{reviewNo}")
    public ResponseEntity<?> deleteRoomReview(@PathVariable Long comNo, @PathVariable Long reviewNo) {
        return communityReviewService.deleteCommunityReview(comNo, reviewNo);
    }
}
