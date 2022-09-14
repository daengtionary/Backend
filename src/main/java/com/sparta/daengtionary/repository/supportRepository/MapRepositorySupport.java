package com.sparta.daengtionary.repository.supportRepository;


import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.daengtionary.configration.error.CustomException;
import com.sparta.daengtionary.configration.error.ErrorCode;
import com.sparta.daengtionary.domain.map.Map;
import com.sparta.daengtionary.dto.response.community.CommunityResponseDto;
import com.sparta.daengtionary.dto.response.map.MapResponseDto;
import com.sparta.daengtionary.dto.response.trade.TradeResponseDto;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    public PageImpl<MapResponseDto> findAllByMap(String category, String title, String content,
                                                 String nick, String address, String direction, Pageable pageable) {
        List<MapResponseDto> responseDtos = queryFactory
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
                .where(eqCategory(category, "map"),
                        eqMapAddress(address),
                        eqTitle(title, "map"),
                        eqContent(content, "map"),
                        eqNick(nick, "map"))
                .groupBy(map.mapNo)
                .orderBy(mapSort(pageable, direction, "map"))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(responseDtos, pageable, responseDtos.size());
    }

    public PageImpl<CommunityResponseDto> findAllByCommunity(String title, String content, String nick,
                                                             String direction, Pageable pageable) {
        List<CommunityResponseDto> responseDtos = queryFactory
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
                .where(eqTitle(title, "community"),
                        eqContent(content, "community"),
                        eqNick(nick, "community"))
                .groupBy(community.communityNo)
                .orderBy(mapSort(pageable, direction, "community"))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(responseDtos, pageable, responseDtos.size());
    }

    public PageImpl<TradeResponseDto> findAllByTrade(String title, String content, String nick, String status, String categoty,
                                                     String direction, int minPrice, int maxPrice, Pageable pageable) {
        List<TradeResponseDto> responseDtos = queryFactory
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
                .where(eqTitle(title, "trade"),
                        eqContent(content, "trade"),
                        eqNick(nick, "trade"),
                        betPrice(minPrice, maxPrice),
                        eqCategory(categoty, "trade"),
                        eqStatus(status))
                .groupBy(trade.tradeNo)
                .orderBy(mapSort(pageable, direction, "trade"))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(responseDtos, pageable, responseDtos.size());
    }

    private BooleanExpression eqCategory(String category, String tableName) {
        if (category.isEmpty()) return null;
        if (tableName.equals("map")) return map.category.eq(category);
        if (tableName.equals("trade")) return trade.category.eq(category);
        return null;
    }

    private BooleanExpression eqMapAddress(String address) {
        return address.isEmpty() ? null : map.address.contains(address);
    }

    private BooleanExpression eqTitle(String title, String tableName) {
        if (title.isEmpty()) return null;
        if (tableName.equals("map")) return map.title.contains(title);
        if (tableName.equals("community")) return community.title.contains(title);
        if (tableName.equals("trade")) return trade.title.contains(title);
        return null;
    }

    private BooleanExpression eqContent(String content, String tableName) {
        if (content.isEmpty()) return null;
        if (tableName.equals("map")) return map.content.contains(content);
        if (tableName.equals("community")) return community.content.contains(content);
        if (tableName.equals("trade")) return trade.content.contains(content);

        return null;
    }

    private BooleanExpression eqNick(String nick, String tableName) {
        if (nick.isEmpty()) return null;
        if (tableName.equals("map")) return map.member.nick.eq(nick);
        if (tableName.equals("community")) return community.member.nick.eq(nick);
        if (tableName.equals("trade")) return trade.member.nick.eq(nick);

        return null;
    }

    private BooleanExpression eqStatus(String status) {
        return status.isEmpty() ? null : trade.status.eq(status);
    }


    private BooleanExpression betPrice(int minPrice, int maxPrice) {
        return minPrice == 0 && maxPrice == 0 ? null : trade.price.between(minPrice, maxPrice);
    }

    private OrderSpecifier<?> mapSort(Pageable pageable, String direction, String tableName) {
        if (direction.isEmpty()) throw new CustomException(ErrorCode.MAP_WRONG_INPUT);

        if (tableName.equals("map")) {
            if (!pageable.getSort().isEmpty()) {

                for (Sort.Order order : pageable.getSort()) {
                    Order dir = direction.equals("asc") ? Order.ASC : Order.DESC;
                    switch (order.getProperty()) {
                        case "new":
                            return new OrderSpecifier<>(dir, map.createdAt);
                        case "popular":
                            return new OrderSpecifier<>(dir, map.view);
                    }
                }
            }
        }
        if (tableName.equals("community")) {
            if (!pageable.getSort().isEmpty()) {
                for (Sort.Order order : pageable.getSort()) {
                    Order dir = direction.equals("asc") ? Order.ASC : Order.DESC;
                    switch (order.getProperty()) {
                        case "new":
                            return new OrderSpecifier<>(dir, community.createdAt);
                        case "popular":
                            return new OrderSpecifier<>(dir, community.view);
                    }
                }
            }
        }
        if (tableName.equals("trade")) {
            if (!pageable.getSort().isEmpty()) {

                for (Sort.Order order : pageable.getSort()) {
                    Order dir = direction.equals("asc") ? Order.ASC : Order.DESC;
                    switch (order.getProperty()) {
                        case "new":
                            return new OrderSpecifier<>(dir, trade.createdAt);
                        case "popular":
                            return new OrderSpecifier<>(dir, trade.view);
                    }
                }
            }
        }

        return null;
    }

}
