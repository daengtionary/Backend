package com.sparta.daengtionary.aop.supportrepository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.daengtionary.category.recommend.domain.Map;
import com.sparta.daengtionary.category.recommend.dto.response.MapDetailResponseDto;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import static com.sparta.daengtionary.category.trade.domain.QTrade.trade;
import static com.sparta.daengtionary.category.trade.domain.QTradeImg.tradeImg1;
import static com.sparta.daengtionary.category.trade.domain.QTradeReview.tradeReview;
import static com.sparta.daengtionary.category.recommend.domain.QMap.map;
import static com.sparta.daengtionary.category.recommend.domain.QMapImg.mapImg;
import static com.sparta.daengtionary.category.recommend.domain.QMapInfo.mapInfo1;
import static com.sparta.daengtionary.category.recommend.domain.QMapReview.mapReview;
import static com.sparta.daengtionary.category.community.domain.QCommunity.community;
import static com.sparta.daengtionary.category.community.domain.QCommunityImg.communityImg1;
import static com.sparta.daengtionary.category.community.domain.QCommunityReview.communityReview;
import static com.sparta.daengtionary.category.wish.domain.QWish.wish;

import java.util.List;

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
                        map.star

                ))
                .from(map)
                .innerJoin(map.member)
                .leftJoin(mapImg)
                .on(map.mapNo.eq(mapImg.map.mapNo))
                .fetchJoin()
                .leftJoin(mapImg)
                .on(map.mapNo.eq(mapImg.map.mapNo))
                .leftJoin(mapInfo1)
                .on(map.mapNo.eq(mapInfo1.map.mapNo))
                .leftJoin(mapReview)
                .on(map.mapNo.eq(mapReview.map.mapNo))
                .leftJoin(wish)
                .on(map.mapNo.eq(wish.map.mapNo))
                .where(map.mapNo.eq(mapNo))
                .orderBy(map.createdAt.desc())
//                .offset(pageable.getOffset())
//                .limit(pageable.getPageSize())
                .fetch();

//        return new PageImpl<>(responseDtos,pageable,responseDtos.size());
    }
//
//
//    public PageImpl<CommunityDetatilResponseDto> findByCommunityDetail(){
//
//    }
//
//    public PageImpl<TradeDetailResponseDto> findByTradeDetail(){
//
//    }
}
