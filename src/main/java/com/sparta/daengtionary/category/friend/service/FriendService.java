package com.sparta.daengtionary.category.friend.service;

import com.sparta.daengtionary.aop.dto.ResponseBodyDto;
import com.sparta.daengtionary.aop.exception.CustomException;
import com.sparta.daengtionary.aop.exception.ErrorCode;
import com.sparta.daengtionary.aop.jwt.TokenProvider;
import com.sparta.daengtionary.aop.supportrepository.PostRepositorySupport;
import com.sparta.daengtionary.category.friend.domain.Friend;
import com.sparta.daengtionary.category.friend.dto.request.FriendRequestDto;
import com.sparta.daengtionary.category.friend.dto.response.FriendResponseDto;
import com.sparta.daengtionary.category.friend.repository.FriendRepository;
import com.sparta.daengtionary.category.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendService {
    private final FriendRepository friendRepository;
    private final ResponseBodyDto responseBodyDto;
    private final TokenProvider tokenProvider;
    private final PostRepositorySupport postRepositorySupport;

    @Transactional
    public ResponseEntity<?> createFriend(FriendRequestDto requestDto) {
        Member member = tokenProvider.getMemberFromAuthentication();

        Friend friend = Friend.builder()
                .member(member)
                .address(requestDto.getAddress())
                .title(requestDto.getTitle())
                .maxCount(requestDto.getMaxCount())
                .content(requestDto.getContent())
                .category(requestDto.getCategory())
                .build();
        friendRepository.save(friend);

        return responseBodyDto.success("생성 완료");
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> getAllFriend(String category, String address, String content, String title, int pagenum, int pagesize) {
        List<FriendResponseDto> friendResponseDtos = postRepositorySupport.findAllFriend(category, address, content, title, pagenum, pagesize);

        return responseBodyDto.success(friendResponseDtos, "조회 완료");
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> getFriend(Long friendNo) {
        Friend friend = findByFriend(friendNo);


        return responseBodyDto.success(FriendResponseDto.builder()
                        .category(friend.getCategory())
                        .address(friend.getAddress())
                        .friendNo(friend.getFriendNo())
                        .content(friend.getContent())
                        .count(friend.getCount())
                        .maxCount(friend.getMaxCount())
                        .createdAt(friend.getCreatedAt())
                        .modifiedAt(friend.getModifiedAt())
                        .status(friend.getStatus())
                        .title(friend.getTitle())
                        .build()
                , "조회 완료"
        );
    }

    @Transactional
    public ResponseEntity<?> friendUpdate(Long friendNo, FriendRequestDto requestDto) {
        Member member = tokenProvider.getMemberFromAuthentication();
        Friend friend = findByFriend(friendNo);
        friend.validateMember(member);

        friend.firendUpdate(requestDto);

        return responseBodyDto.success("수정 성공");
    }

    @Transactional
    public ResponseEntity<?> friendDelete(Long friendNo) {
        Member member = tokenProvider.getMemberFromAuthentication();
        Friend friend = findByFriend(friendNo);
        friend.validateMember(member);

        friendRepository.delete(friend);

        return responseBodyDto.success("삭제 성공");
    }

    @Transactional
    public ResponseEntity<?> friendNotOverCount(Long friendNo) {
        Friend friend = findByFriend(friendNo);

        friend.NotOverCount();

        return responseBodyDto.success("참가 성공");
    }


    public Friend findByFriend(Long friendNo) {
        return friendRepository.findByFriendNo(friendNo).orElseThrow(
                () -> new CustomException(ErrorCode.MAP_NOT_FOUND)
        );
    }
}
