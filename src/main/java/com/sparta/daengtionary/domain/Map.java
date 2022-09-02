package com.sparta.daengtionary.domain;

import com.sparta.daengtionary.configration.error.CustomException;
import com.sparta.daengtionary.configration.error.ErrorCode;
import com.sparta.daengtionary.dto.request.MapPutRequestDto;
import com.sparta.daengtionary.util.Timestamped;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

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
    @Column
    private Double mapx;

    //경도
    @Column
    private Double mapy;

    @OneToMany(mappedBy = "map",fetch = FetchType.LAZY)
    private List<MapImg> mapImgList;

    @OneToMany(mappedBy = "map",fetch = FetchType.LAZY )
    private List<MapInfo> mapInfoList;

    @Builder
    public Map(Long mapNo,Member member,String title,String category,String content,
               String address,Double mapx,Double mapy,List<MapImg> mapImgList,List<MapInfo> mapInfoList){
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
        this.mapImgList = mapImgList;
        this.mapInfoList = mapInfoList;
    }

    public void updateMap(MapPutRequestDto requestDto,List<MapInfo> infos){
        this.title = requestDto.getTitle();
        this.category = requestDto.getCategory();
        this.content = requestDto.getContent();
        this.address = requestDto.getAddress();
        this.mapInfoList = infos;
    }

    public boolean validateMember(Member member){
        if(this.member.equals(member)){
            throw  new CustomException(ErrorCode.MAP_WRONG_ACCESS);
        }
        return !this.member.equals(member);
    }

    public void viewUpdate(int view){
        this.view = view;
    }

    public void starUpdate(float star){
        this.star = star;
    }

}
