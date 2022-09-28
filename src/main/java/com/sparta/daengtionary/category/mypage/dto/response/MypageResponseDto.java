package com.sparta.daengtionary.category.mypage.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.sparta.daengtionary.category.mypage.domain.Dog;
import com.sparta.daengtionary.category.mypage.util.Gender;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

public class MypageResponseDto {
    @Getter
    public static class MemberInfo {
        private Long memberNo;
        private String email;
        private String nick;
        private List<Dog> dogs;
        @JsonFormat(pattern = "yy-MM-dd hh:mm:ss")
        private LocalDateTime createdAt;
        @JsonFormat(pattern = "yy-MM-dd hh:mm:ss")
        private LocalDateTime modifiedAt;


        @Builder
        public MemberInfo(Long memberNo, String email, String nick, List<Dog> dogs,
                          LocalDateTime createdAt, LocalDateTime modifiedAt) {
            this.memberNo = memberNo;
            this.email = email;
            this.nick = nick;
            this.dogs = dogs;
            this.createdAt = createdAt;
            this.modifiedAt = modifiedAt;
        }
    }

    @Getter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class DogInfo {
        private String name;
        private String breed;
        private Gender gender;
        private Float weight;


        @Builder
        public DogInfo(String name, String breed, Gender gender, Float weight) {
            this.name = name;
            this.breed = breed;
            this.gender = gender;
            this.weight = weight;
        }
    }
}