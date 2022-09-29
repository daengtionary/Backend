package com.sparta.daengtionary.category.community.service;

import com.sparta.daengtionary.aop.amazon.AwsS3UploadService;
import com.sparta.daengtionary.aop.dto.ResponseBodyDto;
import com.sparta.daengtionary.aop.exception.CustomException;
import com.sparta.daengtionary.aop.exception.ErrorCode;
import com.sparta.daengtionary.aop.jwt.TokenProvider;
import com.sparta.daengtionary.aop.supportrepository.PostDetailRepositorySupport;
import com.sparta.daengtionary.aop.supportrepository.PostRepositorySupport;
import com.sparta.daengtionary.category.community.domain.Community;
import com.sparta.daengtionary.category.community.domain.CommunityImg;
import com.sparta.daengtionary.category.community.dto.request.CommunityRequestDto;
import com.sparta.daengtionary.category.community.dto.response.CommunityDetailSubResponseDto;
import com.sparta.daengtionary.category.community.dto.response.CommunityDetatilResponseDto;
import com.sparta.daengtionary.category.community.dto.response.CommunityResponseDto;
import com.sparta.daengtionary.category.community.dto.response.CommunityReviewResponseDto;
import com.sparta.daengtionary.category.community.repository.CommunityImgRepository;
import com.sparta.daengtionary.category.community.repository.CommunityRepository;
import com.sparta.daengtionary.category.member.domain.Member;
import com.sparta.daengtionary.category.recommend.dto.response.ImgResponseDto;
import lombok.RequiredArgsConstructor;
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
    private final String imgPath = "/map/image";
    private final PostDetailRepositorySupport postDetailRepositorySupport;

    private List<String> comImgs;

    @Transactional
    public ResponseEntity<?> createCommunity(CommunityRequestDto requestDto, List<MultipartFile> multipartFileList) {
        Member member = tokenProvider.getMemberFromAuthentication();


        Community community = Community.builder()
                .member(member)
                .title(requestDto.getTitle())
                .category(requestDto.getCategory())
                .content(requestDto.getContent())
                .build();

        communityRepository.save(community);

        if (multipartFileList != null) {
            if (multipartFileList.get(0).getSize() > 0) {
                List<String> communityImg = s3UploadService.uploadListImg(multipartFileList, imgPath);

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

            }
        }

        return responseBodyDto.success("커뮤니티 생성 완료");

    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> getCommunitySort(String sort, int pagenum, int pagesize) {
        String category, title, content, nick;
        category = "";
        title = "";
        content = "";
        nick = "";

        List<CommunityResponseDto> responseDtoList = postRepositorySupport.findAllByCommunity(category, title, content, nick, sort, pagenum, pagesize);

        return responseBodyDto.success(responseDtoList, "조회 성공");
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> getSearchCommunity(String category, String title, String content, String nick, String sort, int pagenum, int pagesize) {
        List<CommunityResponseDto> responseDtoList = postRepositorySupport.findAllByCommunity(category, title, content, nick, sort, pagenum, pagesize);
        return responseBodyDto.success(responseDtoList, "조회 성공");
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> getCommunity(Long communityNo, int pagenum, int pagesize) {
        CommunityDetatilResponseDto communityDetatilResponseDto = postDetailRepositorySupport.findByCommunity(communityNo);
        List<ImgResponseDto> imgList = postDetailRepositorySupport.findByCommunityImg(communityNo);
        List<CommunityReviewResponseDto> reviewResponseDtoList = postDetailRepositorySupport.findByCommunityReview(communityNo, pagenum, pagesize);
        return responseBodyDto.success(CommunityDetailSubResponseDto.builder()
                        .communityDetatilResponseDto(communityDetatilResponseDto)
                        .imgResponseDtoList(imgList)
                        .reviewResponseDtos(reviewResponseDtoList)
                        .build()
                , "조회성공");
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

        List<CommunityImg> deleteImg = communityImgRepository.findAllByCommunity(community);
        for (CommunityImg i : deleteImg) {
            s3UploadService.deleteFile(i.getCommunityImg());
        }
        communityImgRepository.deleteAll(deleteImg);
        if (multipartFiles != null) {
            if (multipartFiles.get(0).getSize() > 0) {
                comImgs = s3UploadService.uploadListImg(multipartFiles, imgPath);

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
            }
        }
        community.updateCommunity(requestDto);

        return responseBodyDto.success("수정 성공");
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


}