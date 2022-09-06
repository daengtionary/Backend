package com.sparta.daengtionary.service;

import com.sparta.daengtionary.configration.error.CustomException;
import com.sparta.daengtionary.configration.error.ErrorCode;
import com.sparta.daengtionary.domain.Dog;
import com.sparta.daengtionary.domain.Member;
import com.sparta.daengtionary.dto.request.DogRequestDto;
import com.sparta.daengtionary.dto.request.MemberRequestDto;
import com.sparta.daengtionary.dto.response.MemberResponseDto;
import com.sparta.daengtionary.dto.response.MypageResponseDto;
import com.sparta.daengtionary.dto.response.ResponseBodyDto;
import com.sparta.daengtionary.jwt.TokenProvider;
import com.sparta.daengtionary.repository.DogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MypageService {
    private final TokenProvider tokenProvider;
    private final ResponseBodyDto responseBodyDto;
    private final DogRepository dogRepository;

    @Transactional
    public ResponseEntity<?> updateByNick(MemberRequestDto.Update update) {
        Member member = tokenProvider.getMemberFromAuthentication();
        member.update(update.getNick());

        return responseBodyDto.success("닉네임이 수정되었습니다 :)");
    }

    @Transactional
    public ResponseEntity<?> createDogProfile(DogRequestDto requestDto) {
        Member member = tokenProvider.getMemberFromAuthentication();

        Dog dog = Dog.builder()
                .member(member)
                .name(requestDto.getName())
                .breed(requestDto.getBreed())
                .gender(requestDto.getGender())
                .weight(requestDto.getWeight())
                .build();

        dogRepository.save(dog);

        return responseBodyDto.success(requestDto.getName() + " 프로필이 작성되었습니다 :)");
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> getMemberInfo() {
        Member member = tokenProvider.getMemberFromAuthentication();

        return responseBodyDto.success(MypageResponseDto.MemberInfo.builder()
                        .memberNo(member.getMemberNo())
                        .email(member.getEmail())
                        .nick(member.getNick())
                        .dogs(member.getDogs())
                        .createdAt(member.getCreatedAt())
                        .modifiedAt(member.getModifiedAt())
                        .build()
                , member.getNick() + "님의 정보가 조회되었습니다 :)");
    }

    @Transactional
    public ResponseEntity<?> updateByDog(Long dogNo,
                                         DogRequestDto requestDto) {
        Dog dog = checkByDogInfo(dogNo);

        dog.update(requestDto);

        return responseBodyDto.success(MypageResponseDto.DogInfo.builder()
                        .name(dog.getName())
                        .breed(dog.getBreed())
                        .gender(dog.getGender())
                        .weight(dog.getWeight())
                        .build(),
                requestDto.getName() + " 정보가 수정되었습니다 :)");
    }


    @Transactional(readOnly = true)
    public Dog checkByDogInfo(Long dogNo) {
        Optional<Dog> optionalDog = dogRepository.findById(dogNo);

        return optionalDog.orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_DOG_INFO)
        );
    }
}