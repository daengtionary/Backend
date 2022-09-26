package com.sparta.daengtionary.aop.supportrepository;


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
import static com.sparta.daengtionary.category.mypage.domain.QDog.dog;


import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.daengtionary.category.community.dto.response.CommunityResponseDto;
import com.sparta.daengtionary.category.recommend.domain.Map;
import com.sparta.daengtionary.category.recommend.dto.response.MapResponseDto;
import com.sparta.daengtionary.category.trade.dto.response.TradeResponseDto;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PostRepositorySupport extends QuerydslRepositorySupport {

    private final JPAQueryFactory queryFactory;


    public PostRepositorySupport(JPAQueryFactory queryFactory) {
        super(Map.class);
        this.queryFactory = queryFactory;
    }


    public List<MapResponseDto> findAllByMap(String category, String title, String content,
                                             String nick, String address, String sort, int pagenum, int pagesize) {

        return queryFactory
                .select(Projections.fields(
                        MapResponseDto.class,
                        map.mapNo,
                        map.category,
                        map.title,
                        map.address,
                        map.view,
                        mapReview.countDistinct().as("reviewCount"),
                        wish.countDistinct().as("wishCount"),
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
                .leftJoin(mapReview)
                .on(map.mapNo.eq(mapReview.map.mapNo))
                .leftJoin(wish)
                .on(map.mapNo.eq(wish.map.mapNo))
                .where(eqCategory(category, "map"),
                        eqAddress(address, "map"),
                        eqTitle(title, "map"),
                        eqContent(content, "map"),
                        eqNick(nick, "map"))
                .groupBy(map.mapNo)
                .orderBy(boardSort(sort, "map"), map.mapNo.desc())
                .limit(pagesize)
                .offset((long) pagenum * pagesize)
                .fetch();
    }

    public List<CommunityResponseDto> findAllByCommunity(String category, String title, String content, String nick,
                                                         String sort,  int pagenum, int pagesize) {
        return queryFactory
                .select(Projections.fields(
                        CommunityResponseDto.class,
                        community.communityNo,
                        community.member.nick,
                        community.title,
                        community.view,
                        dog.breed.as("breed"),
                        communityReview.countDistinct().as("reviewCount"),
                        wish.countDistinct().as("wishCount"),
                        communityImg1.communityImg,
                        community.createdAt,
                        community.modifiedAt
                ))
                .from(community)
                .leftJoin(communityImg1)
                .on(community.communityNo.eq(communityImg1.community.communityNo))
                .leftJoin(communityReview)
                .on(community.communityNo.eq(communityReview.community.communityNo))
                .leftJoin(wish)
                .on(community.communityNo.eq(wish.community.communityNo))
                .leftJoin(dog)
                .on(community.member.memberNo.eq(dog.member.memberNo))
                .where(eqTitle(title, "community"),
                        eqContent(content, "community"),
                        eqNick(nick, "community"),
                        eqCategory(category, "community"))
                .groupBy(community.communityNo)
                .orderBy(boardSort(sort, "community"), community.communityNo.desc())
                .limit(pagesize)
                .offset((long) pagenum * pagesize)
                .fetch();

    }

    public List<TradeResponseDto> findAllByTrade(String title, String content, String nick, String address, String postStatus,
                                                 String sort,  int pagenum, int pagesize) {
        return queryFactory
                .select(Projections.fields(
                        TradeResponseDto.class,
                        trade.tradeNo,
                        trade.member.nick,
                        trade.title,
                        trade.content,
                        trade.view,
                        tradeReview.countDistinct().as("reviewCount"),
                        wish.countDistinct().as("wishCount"),
                        tradeImg1.tradeImg,
                        trade.createdAt,
                        trade.modifiedAt
                ))
                .from(trade)
                .leftJoin(tradeImg1)
                .on(trade.tradeNo.eq(tradeImg1.trade.tradeNo))
                .leftJoin(tradeReview)
                .on(trade.tradeNo.eq(tradeReview.trade.tradeNo))
                .leftJoin(wish)
                .on(trade.tradeNo.eq(wish.trade.tradeNo))
                .where(eqTitle(title, "trade"),
                        eqContent(content, "trade"),
                        eqNick(nick, "trade"),
                        eqAddress(address, "trade"),
                        eqPostStatus(postStatus))
                .groupBy(trade.tradeNo)
                .orderBy(boardSort(sort, "trade"), trade.tradeNo.desc())
                .limit(pagesize)
                .offset((long) pagenum * pagesize)
                .fetch();

    }

    private BooleanExpression eqCategory(String category, String tableName) {
        if (category.isEmpty()) return null;
        if (tableName.equals("map")) return map.category.eq(category);
        if (tableName.equals("community")) return community.category.eq(category);
        return null;
    }

    private BooleanExpression eqPostStatus(String postStatus) {
        return postStatus.isEmpty() ? null : trade.postStatus.eq(postStatus);
    }

    private BooleanExpression eqAddress(String address, String tableName) {
        if (address.isEmpty()) return null;
        if (tableName.equals("map")) return map.address.contains(address);
        if (tableName.equals("trade")) return trade.address.contains(address);
        return null;
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

    private OrderSpecifier<?> boardSort(String sort, String tableName) {
        if (!sort.isEmpty()) {
            if (sort.equals("new")) {
                if (tableName.equals("map")) {
                    map.mapNo.desc().nullsLast();
                }
                if (tableName.equals("community")) {
                    community.communityNo.desc().nullsLast();
                }
                if (tableName.equals("trade")) {
                    trade.tradeNo.desc().nullsLast();
                }
            } else if (sort.equals("popular")) {
                if (tableName.equals("map")) {
                    map.view.desc().nullsLast();
                }
                if (tableName.equals("community")) {
                    community.view.desc().nullsLast();
                }
                if (tableName.equals("trade")) {
                    trade.view.desc().nullsLast();
                }
            }
        }
        return null;
    }

}