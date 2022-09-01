package com.sparta.daengtionary.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Getter
@Entity
@Setter
public class MapInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long MapInfoId;

    @JoinColumn(name = "map_id",nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Map map;

    @Column
    private String mapInfo;

    public MapInfo(){

    }

    @Builder
    public MapInfo(Map map,String mapInfo){
        this.map = map;
        this.mapInfo = mapInfo;
    }



}