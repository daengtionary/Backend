package com.sparta.daengtionary.aop.supportrepository;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.daengtionary.category.recommend.domain.Map;
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


    public List<Tuple> findByMapDetail(Long mapNo) {
        return queryFactory
                .select(
                        map,
                        member,
                        mapImg,
                        mapInfo1,
                        mapReview
                )
                .from(map)
                .join(map.member,member)
                .join(map.mapImgs,mapImg)
                .join(map.mapInfos,mapInfo1)
                .join(map.mapReviews,mapReview)
                .where(map.mapNo.eq(mapNo))
                .fetch();
    }
}
