package com.sparta.daengtionary.domain;

import com.sparta.daengtionary.util.Timestamped;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class Map extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mapId;

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

    public Map(){

    }

    @Builder
    public Map(Long mapId,Member member,String title,String category,String content,
               int star,int view,String address,int mapx,int mapy){
        this.mapId = mapId;
        this.member = member;
        this.title = title;
        this.category = category;
        this.content = content;
        this.star = star;
        this.view = view;
        this.address = address;
        this.mapx = mapx;
        this.mapy = mapy;
    }

}
