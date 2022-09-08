package com.sparta.daengtionary.domain.map;

import lombok.*;

import javax.persistence.*;

@Getter
@Entity
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MapInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long MapInfoNo;

    @JoinColumn(name = "mapNo",nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Map map;

    @Column
    private String mapInfo;

    @Builder
    public MapInfo(Map map,String mapInfo){
        this.map = map;
        this.mapInfo = mapInfo;
    }

//    public mapInfoUpdate()

}