package com.sparta.daengtionary.domain;


import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Getter
@Entity
@Setter
public class MapImg {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mapImgId;

    @JoinColumn(name = "map_id",nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Map map;

    @Column(nullable = false)
    private String mapImgName;

    @Column(nullable = false)
    private String mapImgUrl;

    public MapImg(){

    }

    @Builder
    public MapImg(Map map,String mapImgName,String mapImgUrl){
        this.map = map;
        this.mapImgName = mapImgName;
        this.mapImgUrl = mapImgUrl;
    }

    @Builder
    public MapImg(Map map,String mapImgUrl){
        this.map = map;
        this.mapImgUrl = mapImgUrl;
    }

}
