package com.sparta.daengtionary.category.recommend.service;

import com.sparta.daengtionary.aop.amazon.AwsS3UploadService;
import com.sparta.daengtionary.aop.dto.ResponseBodyDto;
import com.sparta.daengtionary.aop.exception.CustomException;
import com.sparta.daengtionary.aop.exception.ErrorCode;
import com.sparta.daengtionary.aop.jwt.TokenProvider;
import com.sparta.daengtionary.aop.supportrepository.PostDetailRepositorySupport;
import com.sparta.daengtionary.aop.supportrepository.PostRepositorySupport;
import com.sparta.daengtionary.category.member.domain.Member;
import com.sparta.daengtionary.category.recommend.domain.Map;
import com.sparta.daengtionary.category.recommend.domain.MapImg;
import com.sparta.daengtionary.category.recommend.domain.MapInfo;
import com.sparta.daengtionary.category.recommend.dto.request.MapPutRequestDto;
import com.sparta.daengtionary.category.recommend.dto.request.MapRequestDto;
import com.sparta.daengtionary.category.recommend.dto.response.*;
import com.sparta.daengtionary.category.recommend.repository.MapImgRepository;
import com.sparta.daengtionary.category.recommend.repository.MapInfoRepository;
import com.sparta.daengtionary.category.recommend.repository.MapRepository;
import com.sparta.daengtionary.category.recommend.repository.MapReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MapService {
    private final MapRepository mapRepository;
    private final MapInfoRepository mapInfoRepository;
    private final MapImgRepository mapImgRepository;
    private final ResponseBodyDto responseBodyDto;
    private final TokenProvider tokenProvider;
    private final PostRepositorySupport postRepositorySupport;
    private final AwsS3UploadService s3UploadService;
    private final MapReviewRepository mapReviewRepository;

    private final PostDetailRepositorySupport postDetailRepositorySupport;

    private final String imgPath = "/map/image";

    @Transactional
    public ResponseEntity<?> createMap(MapRequestDto mapRequestDto, List<MultipartFile> multipartFiles) {
        Member member = tokenProvider.getMemberFromAuthentication();
        validateMemberRole(member);
        validateFile(multipartFiles);
        List<String> mapImgs = s3UploadService.uploadListImg(multipartFiles, imgPath);

        Map map = Map.builder()
                .member(member)
                .title(mapRequestDto.getTitle())
                .content(mapRequestDto.getContent())
                .category(mapRequestDto.getCategory())
                .address(mapRequestDto.getAddress())
                .build();

        mapRepository.save(map);


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
                        .nick(member.getNick())
                        .category(map.getCategory())
                        .content(map.getContent())
                        .title(map.getTitle())
                        .address(map.getAddress())
                        .imgUrls(mapImgs)
                        .createdAt(map.getCreatedAt())
                        .modifiedAt(map.getModifiedAt())
                        .build(),
                "생성 완료"
        );
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> getAllMapByCategory(String category, String address, String sort, int pagenum, int pagesize) {
        String title, content, nick;
        title = "";
        content = "";
        nick = "";

        List<MapResponseDto> mapResponseDtoPage = postRepositorySupport.findAllByMap(category, title, content, nick, address, sort, pagenum, pagesize);
        return responseBodyDto.success(mapResponseDtoPage, "조회 성공");

    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> getSearchMap(String category, String title, String content, String nick, String address, String sort, int pagenum, int pagesize) {
        List<MapResponseDto> mapResponseDtoPage = postRepositorySupport.findAllByMap(category, title, content, nick, address, sort, pagenum, pagesize);
        return responseBodyDto.success(mapResponseDtoPage, "조회 성공");
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> getAllMap(Long mapNo, int pagenum, int pagesize) {
        MapDetailSubResponseDto map = postDetailRepositorySupport.findByMapDetail(mapNo);
        List<ImgResponseDto> imgResponseDtoList = postDetailRepositorySupport.findByMapImg(mapNo);
        List<ReviewResponseDto> reviewResponseDtoList = postDetailRepositorySupport.findByMapReview(mapNo, pagenum, pagesize);
        return responseBodyDto.success(MapSubResponseDto.builder()
                        .mapDetailSubResponseDto(map)
                        .imgResponseDtoList(imgResponseDtoList)
                        .reviewResponseDtoList(reviewResponseDtoList)
                        .build()
                , "조회 성공");
    }


    @Transactional
    public ResponseEntity<?> mapUpdate(MapPutRequestDto requestDto, Long mapNo, List<MultipartFile> multipartFiles) {
        Member member = tokenProvider.getMemberFromAuthentication();
        Map map = validateMap(mapNo);
        map.validateMember(member);
        validateFile(multipartFiles);


        List<MapImg> temp = mapImgRepository.findAllByMap(map);
        for (MapImg i : temp) {
            s3UploadService.deleteFile(i.getMapImgUrl());
        }
        mapImgRepository.deleteAll(temp);

        List<String> mapImgs = s3UploadService.uploadListImg(multipartFiles, imgPath);

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

        map.updateMap(requestDto);

        return responseBodyDto.success(MapDetailResponseDto.builder()
                        .mapNo(map.getMapNo())
                        .nick(map.getMember().getNick())
                        .title(map.getTitle())
                        .address(map.getAddress())
                        .category(map.getCategory())
                        .content(map.getContent())
                        .mapStar(map.getStar())
                        .view(map.getView())
                        .imgUrls(mapImgs)
                        .createdAt(map.getCreatedAt())
                        .modifiedAt(map.getModifiedAt())
                        .build(),

                "수정 성공"
        );
    }

    @Transactional
    public ResponseEntity<?> mapDelete(Long mapNo) {
        Member member = tokenProvider.getMemberFromAuthentication();
        Map map = validateMap(mapNo);
        map.validateMember(member);
        List<MapInfo> infoDelete = mapInfoRepository.findAllByMap(map);
        mapInfoRepository.deleteAll(infoDelete);
        List<MapImg> imgDelete = mapImgRepository.findAllByMap(map);
        for (MapImg i : imgDelete) {
            s3UploadService.deleteFile(i.getMapImgUrl());
        }
        mapImgRepository.deleteAll(imgDelete);
        mapRepository.delete(map);

        return responseBodyDto.success("삭제 완료");
    }

    @Transactional
    public void mapViewUpdate(Long mapNo) {
        Map map = validateMap(mapNo);
        map.viewUpdate();
    }

    private void validateFile(List<MultipartFile> multipartFiles) {
        if (multipartFiles == null) {
            throw new CustomException(ErrorCode.WRONG_INPUT_CONTENT);
        }
    }


    @Transactional(readOnly = true)
    public void isDuplicateCheck(MapRequestDto requestDto) {
        if (mapRepository.existsByTitle(requestDto.getTitle()) &&
                mapRepository.existsByAddress(requestDto.getAddress()) &&
                mapRepository.existsByCategory(requestDto.getCategory())) {
            throw new CustomException(ErrorCode.MAP_DUPLICATE_TITLE);
        }
    }

    @Transactional(readOnly = true)
    public Map validateMap(Long mapNo) {
        return mapRepository.findById(mapNo).orElseThrow(
                () -> new CustomException(ErrorCode.MAP_NOT_FOUND)
        );
    }

    private void validateMemberRole(Member member) {
        String temp = String.valueOf(member.getRole());
        if (!temp.equals("BUSINESS")) {
            throw new CustomException(ErrorCode.MAP_WRONG_ROLE);
        }
    }

}