package com.sparta.daengtionary.dto.request;

import com.sparta.daengtionary.util.Gender;
import lombok.Getter;

@Getter
public class DogRequestDto {
    private String name;
    private String breed;
    private Gender gender;
    private Float weight;
    private String image;
}