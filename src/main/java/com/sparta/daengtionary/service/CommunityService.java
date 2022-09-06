package com.sparta.daengtionary.service;

import com.sparta.daengtionary.configration.error.CustomException;
import com.sparta.daengtionary.configration.error.ErrorCode;
import com.sparta.daengtionary.domain.Community;
import com.sparta.daengtionary.domain.CommunityImg;
import com.sparta.daengtionary.domain.Member;
import com.sparta.daengtionary.dto.request.CommunityRequestDto;
import com.sparta.daengtionary.dto.response.CommunityResponseDto;
import com.sparta.daengtionary.dto.response.ResponseBodyDto;
import com.sparta.daengtionary.repository.CommunityImgRepository;
import com.sparta.daengtionary.repository.CommunityRepository;
import com.sparta.daengtionary.repository.MemberRepository;
import com.sparta.daengtionary.repository.supportRepository.MapRepositorySupport;
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

    private final MapRepositorySupport mapRepositorySupport;

    private final CommunityRepository communityRepository;

    private final CommunityImgRepository communityImgRepository;

    private final MemberRepository memberRepository;


    @Transactional
    public ResponseEntity<?> createCommunity(CommunityRequestDto requestDto, List<MultipartFile> multipartFileList) {
        Member member = validateMember(requestDto.getMemberNo());
        validateFile(multipartFileList);
        List<String> communityImg = s3UploadService.upload(multipartFileList);

        Community community = Community.builder()
                .member(member)
                .title(requestDto.getTitle())
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


        return responseBodyDto.success(CommunityResponseDto.builder()
                        .communityNo(community.getCommunityNo())
                        .title(community.getTitle())
                        .content(community.getContent())
                        .view(community.getView())
                        .communityImgUrl(communityImg)
                        .createdAt(community.getCreatedAt())
                        .modifiedAt(community.getModifiedAt())
                        .build()
                , "커뮤니티 생성 완료"
        );

    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> getAllCommunitySort(String sort, Pageable pageable) {
        PageImpl<CommunityResponseDto> responseDtos = mapRepositorySupport.findAllByCommunity(pageable);

        return responseBodyDto.success(responseDtos, "조회 완료");
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> getCommunity(Long communityNo) {
        Community community = validateCommunity(communityNo);

        List<CommunityImg> communityImgs = communityImgRepository.findAllByCommunity(community);
        List<String> ComImgs = new ArrayList<>();
        for (CommunityImg i : communityImgs) {
            ComImgs.add(i.getCommunityImg());
        }

        return responseBodyDto.success(
                CommunityResponseDto.builder()

                        .build(),"조회 성공"
        );
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

    private Member validateMember(Long memberNo) {
        return memberRepository.findById(memberNo).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_USER_INFO)
        );
    }


}
