package com.sparta.daengtionary.category.recommend.repository;

import com.sparta.daengtionary.category.recommend.domain.MapImg;
import com.sparta.daengtionary.category.recommend.domain.Map;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MapImgRepository extends JpaRepository<MapImg,Long> {
    List<MapImg> findAllByMap(Map map);
}
