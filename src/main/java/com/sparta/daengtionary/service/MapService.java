package com.sparta.daengtionary.service;

import com.sparta.daengtionary.configration.error.CustomException;
import com.sparta.daengtionary.configration.error.ErrorCode;
import com.sparta.daengtionary.domain.*;
import com.sparta.daengtionary.dto.request.MapRequestDto;
import com.sparta.daengtionary.dto.response.MapDetailResponseDto;
import com.sparta.daengtionary.dto.response.MapResponseDto;
import com.sparta.daengtionary.dto.response.ResponseBodyDto;
import com.sparta.daengtionary.jwt.TokenProvider;
import com.sparta.daengtionary.repository.MapImgRepository;
import com.sparta.daengtionary.repository.MapInfoRepository;
import com.sparta.daengtionary.repository.MapRepository;
import com.sparta.daengtionary.repository.MemberRepository;
import com.sparta.daengtionary.repository.supportRepository.MapRepositorySupport;
import jdk.jshell.Snippet;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MapService {
    private final MapRepository mapRepository;
    private final MapInfoRepository mapInfoRepository;
    private final MapImgRepository mapImgRepository;
    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;
    private final ResponseBodyDto responseBodyDto;

    private final MapRepositorySupport mapRepositorySupport;

    @Transactional
    public ResponseEntity<?> createMap(MapRequestDto mapRequestDto, List<String> mapImgs) {
        Member member = memberRepository.findById(mapRequestDto.getMemberNo()).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_USER_INFO)
        );

//        isTitleCheck(mapRequestDto.getTitle());

        Map map = Map.builder()
                .member(member)
                .title(mapRequestDto.getTitle())
                .content(mapRequestDto.getContent())
                .category(mapRequestDto.getCategory())
                .address(mapRequestDto.getAddress())
                .mapx(mapRequestDto.getMapx())
                .mapy(mapRequestDto.getMapy())
                .build();

        mapRepository.save(map);

        List<MapInfo> mapInfos = new ArrayList<>();

        for (String mapinfo : mapRequestDto.getMapInfos()) {
            mapInfos.add(
                    MapInfo.builder()
                            .map(map)
                            .mapInfo(mapinfo)
                            .build()
            );
        }
        mapInfoRepository.saveAll(mapInfos);

        List<MapImg> mapImgList = new ArrayList<>();
        for (String img : mapImgs) {
            mapImgList.add(
                    MapImg.builder()
                            .map(map)
                            .mapImgUrl(img)
                            .build()
            );
        }
        mapImgRepository.saveAll(mapImgList);

        return responseBodyDto.success(
                MapDetailResponseDto.builder()
                        .mapNo(map.getMapNo())
                        .category(map.getCategory())
                        .title(map.getTitle())
                        .address(map.getAddress())
                        .mapx(map.getMapx())
                        .mapy(map.getMapy())
                        .mapInfo(mapRequestDto.getMapInfos())
                        .imgUrls(mapImgs)
                        .createdAt(map.getCreatedAt())
                        .moditiedAt(map.getModifiedAt())
                        .build(), "생성 완료", HttpStatus.OK
        );
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> getAllMap(String category,Pageable pageable) {
        PageImpl<MapResponseDto> mapResponseDtoPage = mapRepositorySupport.findAllByMap(category,pageable);
        return responseBodyDto.success(mapResponseDtoPage, "조회 완료", HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public void isTitleCheck(String title) {
        if (mapRepository.existsByTitle(title)) {
            throw new CustomException(ErrorCode.MAP_DUPLICATE_TITLE);
        }
    }

    @Transactional
    public Member validateMember() {
        return tokenProvider.getMemberFromAuthentication();
    }

}