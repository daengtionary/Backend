package com.sparta.daengtionary.category.recommend.controller;

import com.sparta.daengtionary.category.recommend.service.MapService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/query")
@RequiredArgsConstructor
public class MapController {
    private final MapService mapService;

    @GetMapping("")
    public ResponseEntity<?> getSearchMap(@RequestParam String category, @RequestParam String title, @RequestParam String content, @RequestParam String nick,
                                          @RequestParam String address, @RequestParam String direction, Pageable pageable) {
        return mapService.getSearchMap(category, title, content, nick, address, direction, pageable);
    }

}
