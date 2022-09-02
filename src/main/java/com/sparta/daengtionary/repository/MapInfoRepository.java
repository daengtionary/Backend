package com.sparta.daengtionary.repository;

import com.sparta.daengtionary.domain.Map;
import com.sparta.daengtionary.domain.MapInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MapInfoRepository extends JpaRepository<MapInfo,Long> {
    List<MapInfo> findAllByMap(Map map);
}
