package com.sparta.daengtionary.cotroller;

import com.sparta.daengtionary.dto.response.ResponseBodyDto;
import com.sparta.daengtionary.service.HospitalService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class HospitalController {
    private final HospitalService hospitalService;

    @GetMapping("/search/hospital/{keyword}")
    public ResponseBodyDto get(@PathVariable String keyword){
        return hospitalService.findByKeyword(keyword);
    }
}
