package com.sparta.daengtionary.domain;

import com.sparta.daengtionary.configration.error.CustomException;
import com.sparta.daengtionary.configration.error.ErrorCode;
import com.sparta.daengtionary.dto.request.MapPutRequestDto;
import com.sparta.daengtionary.dto.request.MapRequestDto;
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

    @JoinColumn(name = "member_id", nullable = false)
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

    @OneToMany(mappedBy = "map", fetch = FetchType.LAZY)
    private List<MapImg> mapImgList;

    @OneToMany(mappedBy = "map", fetch = FetchType.LAZY)
    private List<MapInfo> mapInfos;

    @Builder
    public Map(Long mapNo, Member member, String title, String category, String content,
               String address, List<MapImg> mapImgList, List<MapInfo> infos) {
        this.mapNo = mapNo;
        this.member = member;
        this.title = title;
        this.category = category;
        this.content = content;
        this.star = 0;
        this.view = 0;
        this.address = address;
        this.mapImgList = mapImgList;
        this.mapInfos = infos;
    }

    public void updateMap(MapPutRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.category = requestDto.getCategory();
        this.content = requestDto.getContent();
        this.address = requestDto.getAddress();
    }


//    public void updateMap(MapRequestDto requestDto, List<MapImg> mapImgList, List<MapInfo> infos) {
//        this.title = requestDto.getTitle();
//        this.category = requestDto.getCategory();
//        this.content = requestDto.getContent();
//        this.address = requestDto.getAddress();
//        this.mapImgList = mapImgList;
//        this.mapInfos = infos;
//    }

    public void validateMember(Member member) {
        if (!this.member.equals(member)) {
            throw new CustomException(ErrorCode.MAP_WRONG_ACCESS);
        }
    }

    public void viewUpdate() {
        this.view += 1;
    }

    public void starUpdate(float star) {
        this.star = star;
    }

}
