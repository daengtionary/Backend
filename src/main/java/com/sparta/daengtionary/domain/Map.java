package com.sparta.daengtionary.domain;

import com.sparta.daengtionary.util.Timestamped;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Map extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long MapId;

    @Column
    private String title;

    @Column
    private String category;

    @Column
    private String content;

    @Column
    private int star;

    @Column
    private int view;

    @Column
    private String address;

    @Column
    private int mapx;

    @Column
    private int mapy;

}
