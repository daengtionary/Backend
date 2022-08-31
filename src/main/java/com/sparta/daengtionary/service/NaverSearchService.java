package com.sparta.daengtionary.service;

import com.sparta.daengtionary.dto.response.ResponseBodyDto;
import com.sparta.daengtionary.service.naverapi.NaverSearchApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@RequiredArgsConstructor
@Service
public class NaverSearchService {
    private final NaverSearchApiClient naverSearchApiClient;
    private final ResponseBodyDto responseBodyDto;

    @Transactional(readOnly = true)
    public ResponseEntity<?> findByKeyword(String keyword) {
        ArrayList<Object> list = new ArrayList<>();
        list.add(naverSearchApiClient.responseDto(keyword));
        return responseBodyDto.success(list, "", HttpStatus.OK);
    }

}