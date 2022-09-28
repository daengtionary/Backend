package com.sparta.daengtionary.category.community.service;

import com.sparta.daengtionary.aop.dto.ResponseBodyDto;
import com.sparta.daengtionary.aop.exception.CustomException;
import com.sparta.daengtionary.aop.exception.ErrorCode;
import com.sparta.daengtionary.aop.jwt.TokenProvider;
import com.sparta.daengtionary.category.community.domain.Community;
import com.sparta.daengtionary.category.community.domain.CommunityReview;
import com.sparta.daengtionary.category.community.repository.CommunityReviewRepository;
import com.sparta.daengtionary.category.member.domain.Member;
import com.sparta.daengtionary.category.recommend.dto.response.ReviewResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommunityReviewService {
    private final CommunityReviewRepository communityReviewRepository;
    private final ResponseBodyDto responseBodyDto;
    private final CommunityService communityService;
    private final TokenProvider tokenProvider;


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
                .image(communityReview.getMember().getDogs().get(0).getImage())
                .content(communityReview.getContent())
                .build(), "리뷰 생성 완료");
    }

    @Transactional
    public ResponseEntity<?> updateCommunityReview(Long communityNo, Long communityReviewNo, String content) {
        Member member = tokenProvider.getMemberFromAuthentication();
        communityService.validateCommunity(communityNo);
        CommunityReview communityReview = validateCommunityReview(communityReviewNo, member);

        communityReview.communityReviewUpdate(content);

        return responseBodyDto.success(ReviewResponseDto.builder()
                .reviewNo(communityReview.getCommunityReviewNo())
                .nick(communityReview.getMember().getNick())
                .image(communityReview.getMember().getDogs().get(0).getImage())
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
