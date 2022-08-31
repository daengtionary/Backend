package com.sparta.daengtionary.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class MapImg {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mapImgId;

    @JoinColumn(name = "map_id",nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Map map;

    @Column
    private String mapImg;

}
