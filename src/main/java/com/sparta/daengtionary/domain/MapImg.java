package com.sparta.daengtionary.domain;


import lombok.*;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class MapImg {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mapImgNo;

    @JoinColumn(name = "map_id",nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Map map;

    @Column(nullable = false)
    private String imgUrl;

    @Builder
    public MapImg(Map map, String imgUrl){
        this.map = map;
        this.imgUrl = imgUrl;
    }

}
