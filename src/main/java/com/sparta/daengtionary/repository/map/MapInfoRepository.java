package com.sparta.daengtionary.repository.map;

import com.sparta.daengtionary.domain.map.Map;
import com.sparta.daengtionary.domain.map.MapInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MapInfoRepository extends JpaRepository<MapInfo,Long> {
    List<MapInfo> findAllByMap(Map map);
}
