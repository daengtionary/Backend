package com.sparta.daengtionary.domain;

import com.sparta.daengtionary.util.Timestamped;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
    private Long mapNo;

    @JoinColumn(name = "member_id",nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String content;

    @Column
    private float star;

    @Column
    private int view;

    @Column(nullable = false)
    private String address;

    //위도
    @Column(nullable = false)
    private Double mapx;

    //경도
    @Column(nullable = false)
    private Double mapy;

    @Builder
    public Map(Long mapNo,Member member,String title,String category,String content,
               String address,Double mapx,Double mapy){
        this.mapNo = mapNo;
        this.member = member;
        this.title = title;
        this.category = category;
        this.content = content;
        this.star = 0;
        this.view = 0;
        this.address = address;
        this.mapx = mapx;
        this.mapy = mapy;
    }

    public void UpdateMap(){

    }

    public void viewUpdate(int view){
        this.view = view;
    }

    public void starUpdate(float star){
        this.star = star;
    }

}
