package com.sparta.daengtionary.service;

import com.sparta.daengtionary.configration.error.CustomException;
import com.sparta.daengtionary.configration.error.ErrorCode;
import com.sparta.daengtionary.domain.Member;
import com.sparta.daengtionary.domain.map.Map;
import com.sparta.daengtionary.domain.map.MapReview;
import com.sparta.daengtionary.dto.request.ReviewRequestDto;
import com.sparta.daengtionary.dto.response.ResponseBodyDto;
import com.sparta.daengtionary.dto.response.ReviewResponseDto;
import com.sparta.daengtionary.jwt.TokenProvider;
import com.sparta.daengtionary.repository.map.MapRepository;
import com.sparta.daengtionary.repository.map.MapReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MapReviewService {

    private final AwsS3UploadService s3UploadService;
    private final MapReviewRepository mapReviewRepository;
    private final ResponseBodyDto responseBodyDto;
    private final MapService mapService;
    private final TokenProvider tokenProvider;
    private final MapRepository mapRepository;

    private final String imgPath = "/map/review";

    @Transactional
    public ResponseEntity<?> createMapReview(Long mapNo, ReviewRequestDto requestDto, MultipartFile requestImgUrl) {
        Member member = tokenProvider.getMemberFromAuthentication();
        Map map = mapService.validateMap(mapNo);
        validateFile(requestImgUrl);
        String reviewImg = s3UploadService.uploadImage(requestImgUrl, imgPath);

        MapReview mapReview = MapReview.builder()
                .member(member)
                .map(map)
                .content(requestDto.getContent())
                .star(requestDto.getStar())
                .imgUrl(reviewImg)
                .build();

        mapReviewRepository.save(mapReview);
        map.starUpdate(getReviewStarAvg(map));

        return responseBodyDto.success(ReviewResponseDto.builder()
                .reviewNo(mapReview.getMapReviewNo())
                .nick(mapReview.getMember().getNick())
                .content(mapReview.getContent())
                .star(mapReview.getStar())
                .imgUrl(mapReview.getImgUrl())
                .build(), "리뷰 생성 완료");
    }

    @Transactional
    public ResponseEntity<?> updateMapReview(Long mapNo, Long mapReviewNo, ReviewRequestDto requestDto, MultipartFile multipartFile) {
        Member member = tokenProvider.getMemberFromAuthentication();
        mapService.validateMap(mapNo);
        MapReview mapReview = validateMapReview(mapReviewNo, member);
        s3UploadService.deleteFile(mapReview.getImgUrl());
        String reviewImg = s3UploadService.uploadImage(multipartFile, imgPath);

        mapReview.mapReviewUpdate(requestDto, reviewImg);

        return responseBodyDto.success(
                ReviewResponseDto.builder()
                        .reviewNo(mapReview.getMapReviewNo())
                        .nick(mapReview.getMember().getNick())
                        .content(mapReview.getContent())
                        .star(mapReview.getStar())
                        .imgUrl(mapReview.getImgUrl())
                        .build(), "수정 성공"
        );
    }

    @Transactional
    public ResponseEntity<?> deleteMapReview(Long mapNo, Long mapReviewNo) {
        Member member = tokenProvider.getMemberFromAuthentication();
        Map map = mapService.validateMap(mapNo);
        MapReview mapReview = validateMapReview(mapReviewNo, member);
        s3UploadService.deleteFile(mapReview.getImgUrl());
        mapReviewRepository.delete(mapReview);

        return responseBodyDto.success("삭제 성공");
    }

    @Transactional(readOnly = true)
    public float getReviewStarAvg(Map map) {
        List<MapReview> reviewList = mapReviewRepository.findAllByMapOrderByCreatedAtDesc(map);
        float starAvg = 0.0f;
        for (MapReview i : reviewList) {
            starAvg += (float) i.getStar();
        }
        float size = reviewList.size();
        starAvg = (float) starAvg / size;
        return (float) (Math.round(starAvg * 1000) /1000.0);
    }


    private void validateFile(MultipartFile multipartFile) {
        if (multipartFile == null) {
            throw new CustomException(ErrorCode.WRONG_INPUT_CONTENT);
        }
    }

    private MapReview validateMapReview(Long mapReviewNo, Member member) {
        MapReview mapReview = mapReviewRepository.findById(mapReviewNo).orElseThrow(
                () -> new CustomException(ErrorCode.REVIEW_NOT_FOUND)
        );
        mapReview.validateMember(member);
        return mapReview;
    }


}
