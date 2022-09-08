package com.sparta.daengtionary.repository.supportRepository;


import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.daengtionary.domain.map.Map;
import com.sparta.daengtionary.dto.response.community.CommunityResponseDto;
import com.sparta.daengtionary.dto.response.map.MapResponseDto;
import com.sparta.daengtionary.dto.response.trade.TradeResponseDto;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.sparta.daengtionary.domain.map.QMap.map;
import static com.sparta.daengtionary.domain.map.QMapImg.mapImg;
import static com.sparta.daengtionary.domain.map.QMapInfo.mapInfo1;
import static com.sparta.daengtionary.domain.community.QCommunity.community;
import static com.sparta.daengtionary.domain.community.QCommunityImg.communityImg1;
import static com.sparta.daengtionary.domain.trade.QTrade.trade;
import static com.sparta.daengtionary.domain.trade.QTradeImg.tradeImg1;

@Repository
public class MapRepositorySupport extends QuerydslRepositorySupport {

    private final JPAQueryFactory queryFactory;


    public MapRepositorySupport(JPAQueryFactory queryFactory) {
        super(Map.class);
        this.queryFactory = queryFactory;
    }

    public PageImpl<MapResponseDto> findAllByMap(String category, Pageable pageable) {
        List<MapResponseDto> content = queryFactory
                .select(Projections.fields(
                        MapResponseDto.class,
                        map.mapNo,
                        map.category,
                        map.title,
                        map.address,
                        map.view,
                        mapImg.mapImgUrl,
                        mapInfo1.mapInfo,
                        map.createdAt,
                        map.modifiedAt
                ))
                .from(map)
                .leftJoin(mapImg)
                .on(map.mapNo.eq(mapImg.map.mapNo))
                .leftJoin(mapInfo1)
                .on(map.mapNo.eq(mapInfo1.map.mapNo))
                .where(map.category.eq(category))
                .groupBy(map.mapNo)
                .orderBy(map.mapNo.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(content, pageable, content.size());
    }


    public PageImpl<MapResponseDto> findAllByMapByPopular(String category, Pageable pageable) {
        List<MapResponseDto> content = queryFactory
                .select(Projections.fields(
                        MapResponseDto.class,
                        map.mapNo,
                        map.category,
                        map.title,
                        map.address,
                        mapImg.mapImgUrl,
                        mapInfo1.mapInfo,
                        map.createdAt,
                        map.modifiedAt
                ))
                .from(map)
                .leftJoin(mapImg)
                .on(map.mapNo.eq(mapImg.map.mapNo))
                .leftJoin(mapInfo1)
                .on(map.mapNo.eq(mapInfo1.map.mapNo))
                .where(map.category.eq(category))
                .groupBy(map.mapNo)
                .orderBy(map.view.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(content, pageable, content.size());
    }

    public PageImpl<CommunityResponseDto> findAllByCommunity(Pageable pageable) {
        List<CommunityResponseDto> content = queryFactory
                .select(Projections.fields(
                        CommunityResponseDto.class,
                        community.communityNo,
                        community.member.nick,
                        community.title,
                        community.view,
                        communityImg1.communityImg,
                        community.createdAt,
                        community.modifiedAt
                ))
                .from(community)
                .leftJoin(communityImg1)
                .on(community.communityNo.eq(communityImg1.community.communityNo))
                .groupBy(community.communityNo)
                .orderBy(community.createdAt.desc(),community.view.desc().nullsLast())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(content, pageable, content.size());
    }

    public PageImpl<TradeResponseDto> findAllByTrade(Pageable pageable){
        List<TradeResponseDto> content = queryFactory
                .select(Projections.fields(
                        TradeResponseDto.class,
                        trade.tradeNo,
                        trade.member.nick,
                        trade.title,
                        trade.content,
                        trade.view,
                        trade.status,
                        tradeImg1.tradeImg,
                        trade.createdAt,
                        trade.modifiedAt
                ))
                .from(trade)
                .leftJoin(tradeImg1)
                .on(trade.tradeNo.eq(tradeImg1.trade.tradeNo))
                .groupBy(trade.tradeNo)
                .orderBy(trade.createdAt.desc(),trade.view.desc().nullsLast())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(content,pageable,content.size());
    }


    private BooleanExpression eqCategory(String category) {
        if (category.isEmpty()) return null;
        return map.category.eq(category);
    }

}
