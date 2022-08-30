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

    @JoinColumn(name = "member_id",nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private int star;

    @Column(nullable = false)
    private int view;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private int mapx;

    @Column(nullable = false)
    private int mapy;

}
