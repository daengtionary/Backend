package com.sparta.daengtionary.domain;


import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class MapImg {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mapImgId;

    @JoinColumn(name = "map_id",nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Map map;

    @Column(nullable = false)
    private String mapImgUrl;

    @Builder
    public MapImg(Map map,String mapImgUrl){
        this.map = map;
        this.mapImgUrl = mapImgUrl;
    }

}
