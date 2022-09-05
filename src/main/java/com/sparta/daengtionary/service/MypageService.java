package com.sparta.daengtionary.service;

import com.sparta.daengtionary.domain.Dog;
import com.sparta.daengtionary.domain.Member;
import com.sparta.daengtionary.dto.request.DogRequestDto;
import com.sparta.daengtionary.dto.request.MemberRequestDto;
import com.sparta.daengtionary.dto.response.ResponseBodyDto;
import com.sparta.daengtionary.jwt.TokenProvider;
import com.sparta.daengtionary.repository.DogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class MypageService {
    private final TokenProvider tokenProvider;
    private final ResponseBodyDto responseBodyDto;
    private final DogRepository dogRepository;

    @Transactional
    public ResponseEntity<?> updateByNick(MemberRequestDto.Update update,
                                          HttpServletRequest request) {
        Member member = tokenProvider.getMemberFromAuthentication();
        member.update(update.getNick());

        return responseBodyDto.success("닉네임이 수정되었습니다 :)");
    }

    public ResponseEntity<?> createDogProfile(DogRequestDto requestDto,
                                              HttpServletRequest request) {
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
}