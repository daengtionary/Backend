package com.sparta.daengtionary.repository.map;

import com.sparta.daengtionary.domain.map.Map;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MapRepository extends JpaRepository<Map,Long> {

    boolean existsByTitle(String title);

    boolean existsByCategory(String category);

    boolean existsByAddress(String address);

}
