package com.sparta.daengtionary.category.mypage.repository;

import com.sparta.daengtionary.category.mypage.domain.Dog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DogRepository extends JpaRepository<Dog, Long> {
}
