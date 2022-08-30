package com.sparta.daengtionary.service.naverapi;

import com.sparta.daengtionary.dto.response.HospitalResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
@Service
public class HospitalApiClient {

    @Value("${naver.key}")
    private String CLIENT_ID;
    @Value("${naver.password}")
    private String CLIENT_SECRET;

    @Value("${naver.search}")
    private String OpenNaverHospitalUrl_getHospital;


    public HospitalResponseDto responseDto(String keyword){
        final HttpHeaders headers = new HttpHeaders(); // 헤더에 key들을 담아준다.
        headers.set("X-Naver-Client-Id", CLIENT_ID);
        headers.set("X-Naver-Client-Secret", CLIENT_SECRET);

        final HttpEntity<String> entity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();
        ByteBuffer buffer = StandardCharsets.UTF_8.encode(keyword);
        String encode = StandardCharsets.UTF_8.decode(buffer).toString();

        return restTemplate.exchange(OpenNaverHospitalUrl_getHospital, HttpMethod.GET, entity, HospitalResponseDto.class, encode,5,1,"random").getBody();
    }
}
