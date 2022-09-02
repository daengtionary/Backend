package com.sparta.daengtionary.repository;

import com.sparta.daengtionary.domain.Map;
import com.sparta.daengtionary.domain.MapImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MapImgRepository extends JpaRepository<MapImg,Long> {
    List<MapImg> findAllByMap(Map map);
}
