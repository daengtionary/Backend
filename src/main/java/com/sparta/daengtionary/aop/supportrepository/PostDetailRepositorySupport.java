package com.sparta.daengtionary.aop.supportrepository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.daengtionary.category.recommend.domain.Map;
import com.sparta.daengtionary.category.recommend.dto.response.MapDetailTestResponseDto;
import com.sparta.daengtionary.category.recommend.dto.response.MapImgResponseDto;
import com.sparta.daengtionary.category.recommend.dto.response.MapInfoResponseDto;
import com.sparta.daengtionary.category.recommend.dto.response.ReviewResponseDto;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.sparta.daengtionary.category.recommend.domain.QMap.map;
import static com.sparta.daengtionary.category.member.domain.QMember.member;
import static com.sparta.daengtionary.category.recommend.domain.QMapImg.mapImg;
import static com.sparta.daengtionary.category.recommend.domain.QMapInfo.mapInfo1;
import static com.sparta.daengtionary.category.recommend.domain.QMapReview.mapReview;


@Repository
public class PostDetailRepositorySupport extends QuerydslRepositorySupport {

    private final JPAQueryFactory queryFactory;


    public PostDetailRepositorySupport(JPAQueryFactory queryFactory) {
        super(Map.class);
        this.queryFactory = queryFactory;
    }


    public List<Object> findByMapDetail(Long mapNo) {
        MapDetailTestResponseDto dto = queryFactory
                .select(Projections.fields(
                        MapDetailTestResponseDto.class,
                        map.mapNo,
                        map.member.nick,
                        map.title,
                        map.address,
                        map.category,
                        map.content,
                        map.star.as("mapStar"),
                        map.view,
                        map.createdAt,
                        map.modifiedAt
                )).distinct()
                .from(map)
                .join(map.member, member)
                .where(map.mapNo.eq(mapNo))
                .fetchOne();

        List<MapImgResponseDto> mapImgUrl = queryFactory
                .select(Projections.fields(
                        MapImgResponseDto.class,
                        mapImg.mapImgUrl.as("mapImgUrl")
                )).distinct()
                .from(mapImg)
                .join(mapImg.map, map)
                .where(mapImg.map.mapNo.eq(mapNo))
                .fetch();

        List<MapInfoResponseDto> mapInfos = queryFactory
                .select(Projections.fields(
                        MapInfoResponseDto.class,
                        mapInfo1.mapInfo.as("mapInfo")
                )).distinct()
                .from(mapInfo1)
                .join(mapInfo1.map, map)
                .where(mapInfo1.map.mapNo.eq(mapNo))
                .fetch();

        List<ReviewResponseDto> reviews = queryFactory
                .select(Projections.fields(
                        ReviewResponseDto.class,
                        mapReview.mapReviewNo.as("reviewNo"),
                        mapReview.member.nick,
                        mapReview.content,
                        mapReview.star
                )).distinct()
                .from(mapReview)
                .join(mapReview.map,map)
                .where(mapReview.map.mapNo.eq(mapNo))
                .fetch();


        List<Object> list = new ArrayList<>();
        list.add(dto);
        list.add(mapImgUrl);
        list.add(mapInfos);
        list.add(reviews);

        return list;
    }
}
