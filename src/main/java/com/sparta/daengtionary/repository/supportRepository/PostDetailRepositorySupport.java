package com.sparta.daengtionary.repository.supportRepository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.daengtionary.domain.map.Map;
import com.sparta.daengtionary.dto.response.community.CommunityDetatilResponseDto;
import com.sparta.daengtionary.dto.response.map.MapDetailResponseDto;
import com.sparta.daengtionary.dto.response.trade.TradeDetailResponseDto;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import static com.sparta.daengtionary.domain.map.QMap.map;
import static com.sparta.daengtionary.domain.map.QMapImg.mapImg;
import static com.sparta.daengtionary.domain.map.QMapInfo.mapInfo1;
import static com.sparta.daengtionary.domain.map.QMapReview.mapReview;
import static com.sparta.daengtionary.domain.community.QCommunity.community;
import static com.sparta.daengtionary.domain.community.QCommunityImg.communityImg1;
import static com.sparta.daengtionary.domain.community.QCommunityReview.communityReview;
import static com.sparta.daengtionary.domain.trade.QTrade.trade;
import static com.sparta.daengtionary.domain.trade.QTradeImg.tradeImg1;
import static com.sparta.daengtionary.domain.trade.QTradeReview.tradeReview;
import static com.sparta.daengtionary.domain.QWish.wish;


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
//                .leftJoin(mapImg)
//                .on(map.mapNo.eq(mapImg.map.mapNo))
//                .leftJoin(mapInfo1)
//                .on(map.mapNo.eq(mapInfo1.map.mapNo))
//                .leftJoin(mapReview)
//                .on(map.mapNo.eq(mapReview.map.mapNo))
//                .leftJoin(wish)
//                .on(map.mapNo.eq(wish.map.mapNo))
//                .innerJoin(map.mapImgs)
//                .innerJoin(map.mapInfos)
//                .innerJoin(map.reviews)
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
