package com.sparta.daengtionary.repository.map;

import com.sparta.daengtionary.domain.map.Map;
import com.sparta.daengtionary.domain.map.MapReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MapReviewRepository extends JpaRepository<MapReview,Long> {
    List<MapReview> findAllByMapOrderByCreatedAtDesc(Map map);
    List<MapReview> findAllByMap(Map map);
}
