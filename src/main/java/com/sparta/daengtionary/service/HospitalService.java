package com.sparta.daengtionary.service;

import com.sparta.daengtionary.dto.response.ResponseBodyDto;
import com.sparta.daengtionary.service.naverapi.HospitalApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@RequiredArgsConstructor
@Service
public class HospitalService {
    private final HospitalApiClient hospitalApiClient;

    @Transactional(readOnly = true)
    public ResponseBodyDto findByKeyword(String keyword) {
        ArrayList<Object> list = new ArrayList<>();
        list.add(hospitalApiClient.responseDto(keyword));
        return ResponseBodyDto.success(list);
    }
}
