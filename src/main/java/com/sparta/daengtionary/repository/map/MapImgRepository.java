package com.sparta.daengtionary.repository.map;

import com.sparta.daengtionary.domain.map.MapImg;
import com.sparta.daengtionary.domain.map.Map;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MapImgRepository extends JpaRepository<MapImg,Long> {
    List<MapImg> findAllByMap(Map map);
}
