package com.sparta.daengtionary.service;

import com.sparta.daengtionary.configration.error.CustomException;
import com.sparta.daengtionary.configration.error.ErrorCode;
import com.sparta.daengtionary.domain.Member;
import com.sparta.daengtionary.domain.community.Community;
import com.sparta.daengtionary.domain.community.CommunityReview;
import com.sparta.daengtionary.repository.community.CommunityReviewRepository;
import com.sparta.daengtionary.dto.request.ReviewRequestDto;
import com.sparta.daengtionary.dto.response.ResponseBodyDto;
import com.sparta.daengtionary.dto.response.ReviewResponseDto;
import com.sparta.daengtionary.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class CommunityReviewService {
    private final AwsS3UploadService s3UploadService;
    private final CommunityReviewRepository communityReviewRepository;
    private final ResponseBodyDto responseBodyDto;
    private final CommunityService communityService;
    private final TokenProvider tokenProvider;
    private final String imgPath = "/map/review";


    @Transactional
    public ResponseEntity<?> createCommunityReview(Long communityNo, String content) {
        Member member = tokenProvider.getMemberFromAuthentication();
        Community community = communityService.validateCommunity(communityNo);

        CommunityReview communityReview = CommunityReview.builder()
                .member(member)
                .community(community)
                .content(content)
                .build();

        communityReviewRepository.save(communityReview);

        return responseBodyDto.success(ReviewResponseDto.builder()
                .reviewNo(communityReview.getCommunityReviewNo())
                .nick(communityReview.getMember().getNick())
                .memberImgUrl(communityReview.getMember().getDogs().get(0).getImage())
                .content(communityReview.getContent())
                .build(), "리뷰 생성 완료");
    }

    @Transactional
    public ResponseEntity<?> updateCommunityReview(Long communityNo, Long communityReviewNo, ReviewRequestDto requestDto) {
        Member member = tokenProvider.getMemberFromAuthentication();
        communityService.validateCommunity(communityNo);
        CommunityReview communityReview = validateCommunityReview(communityReviewNo, member);

        communityReview.communityReviewUpdate(requestDto);

        return responseBodyDto.success(ReviewResponseDto.builder()
                .reviewNo(communityReview.getCommunityReviewNo())
                .nick(communityReview.getMember().getNick())
                .memberImgUrl(communityReview.getMember().getDogs().get(0).getImage())
                .content(communityReview.getContent())
                .build(), "수정 성공");
    }

    @Transactional
    public ResponseEntity<?> deleteCommunityReview(Long communityNo, Long communityReviewNo) {
        Member member = tokenProvider.getMemberFromAuthentication();
        communityService.validateCommunity(communityNo);
        CommunityReview communityReview = validateCommunityReview(communityReviewNo, member);
        communityReviewRepository.delete(communityReview);

        return responseBodyDto.success("삭제 성공");
    }


    private CommunityReview validateCommunityReview(Long communityReviewNo, Member member) {
        CommunityReview communityReview = communityReviewRepository.findById(communityReviewNo).orElseThrow(
                () -> new CustomException(ErrorCode.REVIEW_NOT_FOUND)
        );
        communityReview.validateMember(member);
        return communityReview;
    }
}
