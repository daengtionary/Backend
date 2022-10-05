package com.sparta.daengtionary.aop.supportrepository;


import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.daengtionary.category.community.dto.response.CommunityResponseDto;
import com.sparta.daengtionary.category.friend.dto.response.FriendResponseDto;
import com.sparta.daengtionary.category.recommend.domain.Map;
import com.sparta.daengtionary.category.recommend.dto.response.MapResponseDto;
import com.sparta.daengtionary.category.trade.dto.response.TradeResponseDto;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.sparta.daengtionary.category.community.domain.QCommunity.community;
import static com.sparta.daengtionary.category.community.domain.QCommunityImg.communityImg1;
import static com.sparta.daengtionary.category.community.domain.QCommunityReview.communityReview;
import static com.sparta.daengtionary.category.friend.domain.QFriend.friend;
import static com.sparta.daengtionary.category.friend.domain.QFriendImg.friendImg1;
import static com.sparta.daengtionary.category.mypage.domain.QDog.dog;
import static com.sparta.daengtionary.category.recommend.domain.QMap.map;
import static com.sparta.daengtionary.category.recommend.domain.QMapImg.mapImg;
import static com.sparta.daengtionary.category.recommend.domain.QMapReview.mapReview;
import static com.sparta.daengtionary.category.trade.domain.QTrade.trade;
import static com.sparta.daengtionary.category.trade.domain.QTradeImg.tradeImg1;
import static com.sparta.daengtionary.category.wish.domain.QWish.wish;

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
                        map.content,
                        mapReview.countDistinct().as("reviewCount"),
                        wish.countDistinct().as("wishCount"),
                        mapImg.mapImgUrl,
                        map.createdAt,
                        map.modifiedAt
                ))
                .from(map)
                .leftJoin(mapImg)
                .on(map.mapNo.eq(mapImg.map.mapNo))
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
                                                         String sort, int pagenum, int pagesize) {
        return queryFactory
                .select(Projections.fields(
                        CommunityResponseDto.class,
                        community.communityNo,
                        community.category,
                        community.member.nick,
                        community.member.email,
                        community.title,
                        community.view,
                        dog.breed.as("breed"),
                        dog.image,
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
                .where(eqCategory(category, "community"),
                        eqTitle(title, "community"),
                        eqNick(nick, "community"),
                        eqContent(content, "community"))
                .groupBy(community.communityNo)
                .orderBy(boardSort(sort, "community"), community.communityNo.desc())
                .limit(pagesize)
                .offset((long) pagenum * pagesize)
                .fetch();

    }

    public List<TradeResponseDto> findAllByTrade(String title, String content, String nick, String address, String postStatus,
                                                 String sort, int pagenum, int pagesize) {
        return queryFactory
                .select(Projections.fields(
                        TradeResponseDto.class,
                        trade.tradeNo,
                        trade.member.nick,
                        trade.title,
                        trade.content,
                        trade.view,
                        trade.price,
                        trade.postStatus,
                        wish.countDistinct().as("wishCount"),
                        tradeImg1.tradeImg,
                        trade.createdAt,
                        trade.modifiedAt
                ))
                .from(trade)
                .leftJoin(tradeImg1)
                .on(trade.tradeNo.eq(tradeImg1.trade.tradeNo))
                .leftJoin(wish)
                .on(trade.tradeNo.eq(wish.trade.tradeNo))
                .where(eqPostStatus(postStatus),
                        eqTitle(title, "trade"),
                        eqAddress(address, "trade"),
                        eqContent(content, "trade"),
                        eqNick(nick, "trade"))
                .groupBy(trade.tradeNo)
                .orderBy(boardSort(sort, "trade"), trade.tradeNo.desc())
                .limit(pagesize)
                .offset((long) pagenum * pagesize)
                .fetch();

    }

    public List<FriendResponseDto> findAllFriend(String category, String address, String content, String title, int pagenum, int pagesize) {
        return queryFactory
                .select(Projections.fields(
                        FriendResponseDto.class,
                        friend.friendNo,
                        friend.category,
                        friend.address,
                        friend.content,
                        friend.title,
                        friend.maxCount,
                        friend.status,
                        friend.count,
                        friendImg1.friendImg,
                        friend.createdAt,
                        friend.modifiedAt
                ))
                .from(friend)
                .leftJoin(friendImg1)
                .on(friend.friendNo.eq(friendImg1.friend.friendNo))
                .where(eqCategory(category, "friend"),
                        eqAddress(address, "friend"),
                        eqTitle(title, "friend"),
                        eqContent(content, "friend")
                )
                .groupBy(friend.friendNo)
                .orderBy(friend.createdAt.desc().nullsLast())
                .limit(pagesize)
                .offset((long) pagenum * pagesize)
                .fetch();
    }

    private BooleanExpression eqCategory(String category, String tableName) {
        if (category.isEmpty()) return null;
        if (tableName.equals("friend")) return friend.category.eq(category);
        if (tableName.equals("map")) return map.category.eq(category);
        if (tableName.equals("community")) return community.category.eq(category);
        return null;
    }

    private BooleanExpression eqPostStatus(String postStatus) {
        return postStatus.isEmpty() ? null : trade.postStatus.eq(postStatus);
    }


    private BooleanExpression eqAddress(String address, String tableName) {
        if (address.isEmpty()) return null;
        if (tableName.equals("friend")) return friend.address.contains(address);
        if (tableName.equals("map")) return map.address.contains(address);
        if (tableName.equals("trade")) return trade.address.contains(address);
        return null;
    }

    private BooleanExpression eqTitle(String title, String tableName) {
        if (title.isEmpty()) return null;
        if (tableName.equals("friend")) return friend.title.contains(title);
        if (tableName.equals("map")) return map.title.contains(title);
        if (tableName.equals("community")) return community.title.contains(title);
        if (tableName.equals("trade")) return trade.title.contains(title);
        return null;
    }

    private BooleanExpression eqContent(String content, String tableName) {
        if (content.isEmpty()) return null;
        if (tableName.equals("friend")) return friend.content.contains(content);
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
                    return map.createdAt.desc().nullsLast();
                }
                if (tableName.equals("community")) {
                    return community.createdAt.desc().nullsLast();
                }
                if (tableName.equals("trade")) {
                    return trade.createdAt.desc().nullsLast();
                }
            } else if (sort.equals("popular")) {
                if (tableName.equals("map")) {
                    return map.view.desc().nullsLast();
                }
                if (tableName.equals("community")) {
                    return community.view.desc().nullsLast();
                }
                if (tableName.equals("trade")) {
                    return trade.view.desc().nullsLast();
                }
            }
        }
        return null;
    }

}