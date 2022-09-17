package com.sparta.daengtionary.category.recommend.repository;

import com.sparta.daengtionary.category.recommend.domain.Map;
import com.sparta.daengtionary.category.recommend.domain.MapInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MapInfoRepository extends JpaRepository<MapInfo,Long> {
    List<MapInfo> findAllByMap(Map map);
}
