package com.sparta.daengtionary.service;

import com.sparta.daengtionary.configration.error.CustomException;
import com.sparta.daengtionary.configration.error.ErrorCode;
import com.sparta.daengtionary.domain.*;
import com.sparta.daengtionary.dto.request.MapPutRequestDto;
import com.sparta.daengtionary.dto.request.MapRequestDto;
import com.sparta.daengtionary.dto.response.MapDetailResponseDto;
import com.sparta.daengtionary.dto.response.MapResponseDto;
import com.sparta.daengtionary.dto.response.MemberResponseDto;
import com.sparta.daengtionary.dto.response.ResponseBodyDto;
import com.sparta.daengtionary.jwt.TokenProvider;
import com.sparta.daengtionary.repository.MapImgRepository;
import com.sparta.daengtionary.repository.MapInfoRepository;
import com.sparta.daengtionary.repository.MapRepository;
import com.sparta.daengtionary.repository.MemberRepository;
import com.sparta.daengtionary.repository.supportRepository.MapRepositorySupport;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
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
        Member member = validateMember(mapRequestDto.getMemberNo());

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
    public ResponseEntity<?> getAllMapByCategory(String category, String orderBy ,Pageable pageable){
        if (orderBy.equals("popular")) {
            PageImpl<MapResponseDto> mapResponseDtoPage = mapRepositorySupport.findAllByMapByPopular(category, pageable);
            return responseBodyDto.success(mapResponseDtoPage, "조회 완료", HttpStatus.OK);
        }
        List<MapResponseDto> mapResponseDtoPage = mapRepositorySupport.findAllByMap(category,pageable);
        return responseBodyDto.success(mapResponseDtoPage, "조회 완료", HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> getAllMap(Long mapNo) {
        Map map = validateMap(mapNo);

        List<MapImg> mapImgs = mapImgRepository.findAllByMap(map);
        List<String> iList = new ArrayList<>();

        for (MapImg i : mapImgs) {
            iList.add(i.getMapImgUrl());
        }

        List<MapInfo> mapInfos = mapInfoRepository.findAllByMap(map);
        List<String> infoList = new ArrayList<>();

        for (MapInfo i : mapInfos) {
            infoList.add(i.getMapInfo());
        }

        return responseBodyDto.success(
                MapDetailResponseDto.builder()
                        .mapNo(map.getMapNo())
                        .member(
                                MemberResponseDto.builder()
                                        .memberNo(map.getMember().getMemberNo())
                                        .email(map.getMember().getEmail())
                                        .role(map.getMember().getRole())
                                        .nick(map.getMember().getNick())
                                        .build()
                        )
                        .title(map.getTitle())
                        .address(map.getAddress())
                        .category(map.getCategory())
                        .content(map.getContent())
                        .star(map.getStar())
                        .view(map.getView())
                        .imgUrls(iList)
                        .mapInfo(infoList)
                        .mapx(map.getMapx())
                        .mapy(map.getMapy())
                        .createdAt(map.getCreatedAt())
                        .moditiedAt(map.getModifiedAt())
                        .build()
                , "조회 성공", HttpStatus.OK
        );
    }

    @Transactional
    public ResponseEntity<?> mapUpdate(MapPutRequestDto putRequestDto, Long mapNo) {
        Member member = validateMember(putRequestDto.getMemberNo());
        Map map = validateMap(mapNo);
        map.validateMember(member);


        List<MapInfo> mapInfos = new ArrayList<>();

        for (String mapinfo : putRequestDto.getMapInfos()) {
            mapInfos.add(
                    MapInfo.builder()
                            .map(map)
                            .mapInfo(mapinfo)
                            .build()
            );
        }

        map.updateMap(putRequestDto, mapInfos);

        return responseBodyDto.success(MapDetailResponseDto.builder()
                .mapNo(map.getMapNo())
                .category(map.getCategory())
                .title(map.getTitle())
                .address(map.getAddress())
                .mapx(map.getMapx())
                .mapy(map.getMapy())
                .mapInfo(putRequestDto.getMapInfos())
                .createdAt(map.getCreatedAt())
                .moditiedAt(map.getModifiedAt())
                .build(), "수정 성공", HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> mapDelete(Long mapNo,Long memberNo) {
        Member member =  validateMember(memberNo);
        Map map = validateMap(mapNo);
        map.validateMember(member);

//        List<MapInfo> mapInfos = mapInfoRepository.findAllByMap(map);
//        mapInfoRepository.deleteAll(mapInfos);
//
//        List<MapImg> mapImgs = mapImgRepository.findAllByMap(map);
//        mapImgRepository.deleteAll(mapImgs);

        mapRepository.delete(map);

        return responseBodyDto.success("삭제 완료");
    }


    @Transactional(readOnly = true)
    public void isDuplicateCheck(String title, String address) {
        if (mapRepository.existsByTitle(title) && mapRepository.existsByAddress(address)) {
            throw new CustomException(ErrorCode.MAP_DUPLICATE_TITLE);
        }
    }

    @Transactional(readOnly = true)
    public Map validateMap(Long mapNo) {
        return mapRepository.findById(mapNo).orElseThrow(
                () -> new CustomException(ErrorCode.MAP_NOT_FOUND)
        );
    }

    private Member validateMember(Long memberNo) {
        return memberRepository.findById(memberNo).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_USER_INFO)
        );
    }
}