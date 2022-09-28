package com.sparta.daengtionary.category.recommend.domain;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class MapImg {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mapImgNo;

    @JoinColumn(name = "mapNo", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Map map;

    @Column(nullable = false)
    private String mapImgUrl;

    @Builder
    public MapImg(Map map, String mapImgUrl) {
        this.map = map;
        this.mapImgUrl = mapImgUrl;
    }

}
