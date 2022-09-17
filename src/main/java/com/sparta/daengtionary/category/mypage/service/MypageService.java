package com.sparta.daengtionary.category.mypage.service;

import com.sparta.daengtionary.aop.amazon.AwsS3UploadService;
import com.sparta.daengtionary.aop.exception.CustomException;
import com.sparta.daengtionary.aop.exception.ErrorCode;
import com.sparta.daengtionary.category.mypage.domain.Dog;
import com.sparta.daengtionary.category.member.domain.Member;
import com.sparta.daengtionary.category.mypage.dto.request.DogRequestDto;
import com.sparta.daengtionary.category.member.dto.request.MemberRequestDto;
import com.sparta.daengtionary.category.mypage.dto.response.MypageResponseDto;
import com.sparta.daengtionary.aop.dto.ResponseBodyDto;
import com.sparta.daengtionary.aop.jwt.TokenProvider;
import com.sparta.daengtionary.category.mypage.repository.DogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MypageService {
    private final TokenProvider tokenProvider;
    private final ResponseBodyDto responseBodyDto;
    private final DogRepository dogRepository;
    private final AwsS3UploadService s3UploadService;

    private final String imgPath = "/dog/image";

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
    public ResponseEntity<?> updateByNick(MemberRequestDto.Update update) {
        Member member = tokenProvider.getMemberFromAuthentication();
        member.update(update.getNick());

        return responseBodyDto.success("닉네임이 수정되었습니다 :)");
    }

    @Transactional
    public ResponseEntity<?> createDogProfile(DogRequestDto requestDto,
                                              MultipartFile multipartFile) {
        Member member = tokenProvider.getMemberFromAuthentication();
        String image = "";

        if (!multipartFile.isEmpty()) {
            validateImageFile(multipartFile);
            image = s3UploadService.uploadImage(multipartFile,imgPath);
        }

        Dog dog = Dog.builder()
                .member(member)
                .name(requestDto.getName())
                .breed(requestDto.getBreed())
                .gender(requestDto.getGender())
                .weight(requestDto.getWeight())
                .image(image)
                .build();

        dogRepository.save(dog);

        return responseBodyDto.success(requestDto.getName() + " 프로필이 작성되었습니다 :)");
    }

    @Transactional
    public ResponseEntity<?> updateByDogProfile(Long dogNo,
                                                DogRequestDto requestDto,
                                                MultipartFile multipartFile) {
        Dog dog = checkByDogInfo(dogNo);

        s3UploadService.deleteFile(dog.getImage());
        validateImageFile(multipartFile);

        String image = s3UploadService.uploadImage(multipartFile,imgPath);

        dog.updateByProfile(requestDto, image);

        return responseBodyDto.success(requestDto.getName() + " 정보가 수정되었습니다 :)");
    }

    @Transactional
    public ResponseEntity<?> updateByDogImage(Long dogNo,
                                              MultipartFile multipartFile) {
        Dog dog = checkByDogInfo(dogNo);
        s3UploadService.deleteFile(dog.getImage());

        validateImageFile(multipartFile);
        String image = s3UploadService.uploadImage(multipartFile,imgPath);

        dog.updateByImage(image);

        return responseBodyDto.success(dog.getName() + " 정보가 수정되었습니다 :)");
    }

    public ResponseEntity<?> deleteByDogProfile(Long dogNo) {
        Dog dog = checkByDogInfo(dogNo);
        dogRepository.delete(dog);

        return responseBodyDto.success(dog.getName() + "정보가 삭제되었습니다 :)");
    }

    public ResponseEntity<?> deleteByDogImage(Long dogNo) {
        Dog dog = checkByDogInfo(dogNo);
        s3UploadService.deleteFile(dog.getImage());

        dog.updateByImage(null);

        return responseBodyDto.success(dog.getName() + "이미지가 삭제되었습니다 :)");
    }


    @Transactional(readOnly = true)
    public Dog checkByDogInfo(Long dogNo) {
        Optional<Dog> optionalDog = dogRepository.findById(dogNo);

        return optionalDog.orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_DOG_INFO)
        );
    }

    public void validateImageFile(MultipartFile multipartFile) {
        if (multipartFile == null) {
            throw new CustomException(ErrorCode.WRONG_INPUT_CONTENT);
        }
    }
}