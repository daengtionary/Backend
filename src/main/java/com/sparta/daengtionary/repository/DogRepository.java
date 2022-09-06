package com.sparta.daengtionary.repository;

import com.sparta.daengtionary.domain.Dog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DogRepository extends JpaRepository<Dog, Long> {
}
