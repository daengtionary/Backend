package com.sparta.daengtionary.aop.supportrepository;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.daengtionary.category.recommend.domain.Map;
import com.sparta.daengtionary.category.recommend.dto.response.MapDetailResponseDto;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.sparta.daengtionary.category.recommend.domain.QMap.map;
import static com.sparta.daengtionary.category.recommend.domain.QMapImg.mapImg;
import static com.sparta.daengtionary.category.member.domain.QMember.member;
import static com.sparta.daengtionary.category.recommend.domain.QMapInfo.mapInfo1;
import static com.sparta.daengtionary.category.recommend.domain.QMapReview.mapReview;

@Repository
public class PostDetailRepositorySupport extends QuerydslRepositorySupport {

    private final JPAQueryFactory queryFactory;


    public PostDetailRepositorySupport(JPAQueryFactory queryFactory) {
        super(Map.class);
        this.queryFactory = queryFactory;
    }


    public List<MapDetailResponseDto> findByMapDetail(Long mapNo) {
        return queryFactory
                .select(Projections.fields(
                        MapDetailResponseDto.class,
                        map.mapNo,
                        map.member.nick,
                        map.title,
                        map.address,
                        map.category,
                        map.content,
                        map.star.as("mapStar"),
                        map.view,
                        map.createdAt,
                        map.modifiedAt,
                        mapImg.mapImgUrl.as("mapImgUrl")
                ))
                .from(map)
                .join(map.member, member)
                .join(mapImg.map,map)
//                .on(map.mapNo.eq(mapImg.map.mapNo))
//                .leftJoin(mapInfo1)
//                .on(map.mapNo.eq(mapInfo1.map.mapNo))
//                .leftJoin(mapReview)
//                .on(map.mapNo.eq(mapReview.map.mapNo))
//                .where(map.mapNo.eq(mapNo))
                .fetch();
    }
}
