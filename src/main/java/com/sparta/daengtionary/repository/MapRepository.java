package com.sparta.daengtionary.repository;

import com.sparta.daengtionary.domain.Map;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MapRepository extends JpaRepository<Map,Long> {

    boolean existsByTitle(String title);

    boolean existsByCategory(String category);

    boolean existsByAddress(String address);

}
