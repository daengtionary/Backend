package com.sparta.daengtionary.service;

import com.sparta.daengtionary.configration.error.CustomException;
import com.sparta.daengtionary.configration.error.ErrorCode;
import com.sparta.daengtionary.domain.community.Community;
import com.sparta.daengtionary.domain.community.CommunityImg;
import com.sparta.daengtionary.domain.Member;
import com.sparta.daengtionary.domain.community.CommunityReview;
import com.sparta.daengtionary.repository.community.CommunityReviewRepository;
import com.sparta.daengtionary.dto.request.CommunityRequestDto;
import com.sparta.daengtionary.dto.response.ReviewResponseDto;
import com.sparta.daengtionary.dto.response.community.CommunityDetatilResponseDto;
import com.sparta.daengtionary.dto.response.community.CommunityResponseDto;
import com.sparta.daengtionary.dto.response.ResponseBodyDto;
import com.sparta.daengtionary.jwt.TokenProvider;
import com.sparta.daengtionary.repository.community.CommunityImgRepository;
import com.sparta.daengtionary.repository.community.CommunityRepository;
import com.sparta.daengtionary.repository.supportRepository.PostRepositorySupport;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommunityService {
    private final AwsS3UploadService s3UploadService;
    private final ResponseBodyDto responseBodyDto;
    private final PostRepositorySupport postRepositorySupport;
    private final CommunityRepository communityRepository;
    private final TokenProvider tokenProvider;
    private final CommunityImgRepository communityImgRepository;
    private final CommunityReviewRepository communityReviewRepository;
    private final String imgPath = "/map/image";

    @Transactional
    public ResponseEntity<?> createCommunity(CommunityRequestDto requestDto, List<MultipartFile> multipartFileList) {
        Member member = tokenProvider.getMemberFromAuthentication();
        validateFile(multipartFileList);
        List<String> communityImg = s3UploadService.uploadListImg(multipartFileList, imgPath);

        Community community = Community.builder()
                .member(member)
                .title(requestDto.getTitle())
                .category(requestDto.getCategory())
                .content(requestDto.getContent())
                .build();

        communityRepository.save(community);

        List<CommunityImg> communityImgs = new ArrayList<>();
        for (String img : communityImg) {
            communityImgs.add(
                    CommunityImg.builder()
                            .community(community)
                            .communityImg(img)
                            .build()
            );
        }

        communityImgRepository.saveAll(communityImgs);


        return responseBodyDto.success(CommunityDetatilResponseDto.builder()
                        .communityNo(community.getCommunityNo())
                        .nick(member.getNick())
                        .title(community.getTitle())
                        .category(community.getCategory())
                        .content(community.getContent())
                        .view(community.getView())
                        .imgList(communityImg)
                        .createdAt(community.getCreatedAt())
                        .modifiedAt(community.getModifiedAt())
                        .build(),
                "커뮤니티 생성 완료"
        );
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> getCommunitySort(String direction, Pageable pageable) {
        String category, title, content, nick;
        category = "";
        title = "";
        content = "";
        nick = "";

        PageImpl<CommunityResponseDto> responseDtoList = postRepositorySupport.findAllByCommunity(category, title, content, nick, direction, pageable);

        return responseBodyDto.success(responseDtoList, "조회 성공");
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> getSearchCommunity(String category, String title, String content, String nick, String direction, Pageable pageable) {
        PageImpl<CommunityResponseDto> responseDtoList = postRepositorySupport.findAllByCommunity(category, title, content, nick, direction, pageable);
        return responseBodyDto.success(responseDtoList, "조회 성공");
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> getCommunity(Long communityNo) {
        Community community = validateCommunity(communityNo);

        List<CommunityImg> communityImgs = communityImgRepository.findAllByCommunity(community);
        List<String> comImgs = new ArrayList<>();
        for (CommunityImg i : communityImgs) {
            comImgs.add(i.getCommunityImg());
        }

        List<CommunityReview> reviews = communityReviewRepository.findAllByCommunity(community);
        List<ReviewResponseDto> reviewResponseDtoList = new ArrayList<>();

        for (CommunityReview i : reviews) {
            reviewResponseDtoList.add(
                    ReviewResponseDto.builder()
                            .reviewNo(i.getCommunityReviewNo())
                            .nick(i.getMember().getNick())
                            .content(i.getContent())
                            .memberImgUrl(i.getMember().getDogs().get(0).getImage())
                            .build()
            );
        }

        return responseBodyDto.success(
                CommunityDetatilResponseDto.builder()
                        .communityNo(community.getCommunityNo())
                        .nick(community.getMember().getNick())
                        .title(community.getTitle())
                        .content(community.getContent())
                        .view(community.getView())
                        .imgList(comImgs)
                        .reviewList(reviewResponseDtoList)
                        .createdAt(community.getCreatedAt())
                        .modifiedAt(community.getModifiedAt())
                        .build(), "조회 성공"
        );
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> getMyPostByCommunity() {
        Member member = tokenProvider.getMemberFromAuthentication();

        List<Community> communityList = communityRepository.findByMember(member);
        List<CommunityResponseDto> communityResponseDtoList = new ArrayList<>();

        for (Community community : communityList) {
            communityResponseDtoList.add(
                    CommunityResponseDto.builder()
                            .communityNo(community.getCommunityNo())
                            .nick(community.getMember().getNick())
                            .title(community.getTitle())
                            .view(community.getView())
                            .communityImg(community.getCommunityImgs().get(0).getCommunityImg())
                            .createdAt(community.getCreatedAt())
                            .modifiedAt(community.getModifiedAt())
                            .build()
            );
        }

        return responseBodyDto.success(communityResponseDtoList, "조회 성공");
    }

    @Transactional
    public ResponseEntity<?> communityUpdate(CommunityRequestDto requestDto, Long communityNo, List<MultipartFile> multipartFiles) {
        Member member = tokenProvider.getMemberFromAuthentication();
        Community community = validateCommunity(communityNo);
        community.validateMember(member);
        validateFile(multipartFiles);

        List<CommunityImg> deleteImg = communityImgRepository.findAllByCommunity(community);
        for (CommunityImg i : deleteImg) {
            s3UploadService.deleteFile(i.getCommunityImg());
        }
        communityImgRepository.deleteAll(deleteImg);

        List<String> comImgs = s3UploadService.uploadListImg(multipartFiles, imgPath);

        List<CommunityImg> saveImg = new ArrayList<>();
        for (String i : comImgs) {
            saveImg.add(
                    CommunityImg.builder()
                            .community(community)
                            .communityImg(i)
                            .build()
            );
        }
        communityImgRepository.saveAll(saveImg);

        community.updateCommunity(requestDto);

        return responseBodyDto.success(CommunityDetatilResponseDto.builder()
                .communityNo(community.getCommunityNo())
                .nick(member.getNick())
                .title(community.getTitle())
                .content(community.getContent())
                .imgList(comImgs)
                .createdAt(community.getCreatedAt())
                .modifiedAt(community.getModifiedAt())
                .build(), "수정 성공");
    }

    @Transactional
    public ResponseEntity<?> communityDelete(Long communityNo) {
        Member member = tokenProvider.getMemberFromAuthentication();
        Community community = validateCommunity(communityNo);
        community.validateMember(member);
        List<CommunityImg> deleteImg = communityImgRepository.findAllByCommunity(community);
        for (CommunityImg i : deleteImg) {
            s3UploadService.deleteFile(i.getCommunityImg());
        }
        communityImgRepository.deleteAll(deleteImg);
        communityRepository.delete(community);

        return responseBodyDto.success("삭제 성공");
    }


    @Transactional
    public void communityViewUpdate(Long communityNo) {
        Community community = validateCommunity(communityNo);
        community.viewUpdate();
    }

    @Transactional(readOnly = true)
    public Community validateCommunity(Long communityNo) {
        return communityRepository.findById(communityNo).orElseThrow(
                () -> new CustomException(ErrorCode.MAP_NOT_FOUND)
        );
    }

    private void validateFile(List<MultipartFile> multipartFiles) {
        if (multipartFiles == null) {
            throw new CustomException(ErrorCode.WRONG_INPUT_CONTENT);
        }
    }
}