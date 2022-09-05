package com.sparta.daengtionary.domain;

import com.sparta.daengtionary.util.Gender;
import lombok.Builder;

import javax.persistence.*;

@Entity
public class Dog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dogNo;
    @JoinColumn(name = "member_no", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String breed;
    @Column(nullable = false)
    private Gender gender;
    @Column(nullable = false)
    private Float weight;


    public Dog() {

    }

    @Builder
    public Dog(Long dogNo, Member member, String name, String breed, Gender gender, Float weight) {
        this.dogNo = dogNo;
        this.member = member;
        this.name = name;
        this.breed = breed;
        this.gender = gender;
        this.weight = weight;
    }

    public void update(String name, String breed, Gender gender, Float weight) {
        this.name = name;
        this.breed = breed;
        this.gender = gender;
        this.weight = weight;
    }
}