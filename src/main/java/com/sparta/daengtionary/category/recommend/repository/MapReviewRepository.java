package com.sparta.daengtionary.category.recommend.repository;

import com.sparta.daengtionary.category.recommend.domain.Map;
import com.sparta.daengtionary.category.recommend.domain.MapReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MapReviewRepository extends JpaRepository<MapReview, Long> {
    List<MapReview> findAllByMapOrderByCreatedAtDesc(Map map);

    List<MapReview> findAllByMap(Map map);
}
