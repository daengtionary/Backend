package com.sparta.daengtionary.category.friend.controller;

import com.sparta.daengtionary.category.friend.dto.request.FriendRequestDto;
import com.sparta.daengtionary.category.friend.service.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/friend")
@RequiredArgsConstructor
public class FriendController {
    private final FriendService friendService;

    @PostMapping("/create")
    public ResponseEntity<?> createFriend(@RequestPart(value = "data") FriendRequestDto requestDto,
                                          @RequestPart(value = "imgUrl", required = false) List<MultipartFile> multipartFiles) {
        return friendService.createFriend(requestDto, multipartFiles);
    }

    @GetMapping("")
    public ResponseEntity<?> getAllFriend(@RequestParam String category,
                                          @RequestParam String address,
                                          @RequestParam String title,
                                          @RequestParam String content,
                                          @RequestParam int pagenum,
                                          @RequestParam int pagesize) {
        return friendService.getAllFriend(category, address, content, title, pagenum, pagesize);
    }

    @GetMapping("/{friendNo}")
    public ResponseEntity<?> getFriend(@PathVariable Long friendNo) {
        return friendService.getFriend(friendNo);
    }

    @PatchMapping("/{friendNo}")
    public ResponseEntity<?> friendUpdate(@PathVariable Long friendNo, @RequestPart(value = "data") FriendRequestDto requestDto) {
        return friendService.friendUpdate(friendNo, requestDto);
    }

    @PostMapping("/count/{friendNo}")
    public ResponseEntity<?> friendCountUpdate(@PathVariable Long friendNo) {
        return friendService.friendNotOverCount(friendNo);
    }

    @DeleteMapping("/{friendNo}")
    public ResponseEntity<?> friendDelete(@PathVariable Long friendNo) {
        return friendService.friendDelete(friendNo);
    }
}