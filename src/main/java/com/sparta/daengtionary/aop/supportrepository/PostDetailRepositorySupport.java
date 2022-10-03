package com.sparta.daengtionary.aop.supportrepository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.daengtionary.category.recommend.domain.Map;
import com.sparta.daengtionary.category.recommend.dto.response.MapDetailSubResponseDto;
import com.sparta.daengtionary.category.recommend.dto.response.MapImgResponseDto;
import com.sparta.daengtionary.category.recommend.dto.response.ReviewResponseDto;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.sparta.daengtionary.category.member.domain.QMember.member;
import static com.sparta.daengtionary.category.mypage.domain.QDog.dog;
import static com.sparta.daengtionary.category.recommend.domain.QMap.map;
import static com.sparta.daengtionary.category.recommend.domain.QMapImg.mapImg;
import static com.sparta.daengtionary.category.recommend.domain.QMapReview.mapReview;


@Repository
public class PostDetailRepositorySupport extends QuerydslRepositorySupport {

    private final JPAQueryFactory queryFactory;


    public PostDetailRepositorySupport(JPAQueryFactory queryFactory) {
        super(Map.class);
        this.queryFactory = queryFactory;
    }


    public MapDetailSubResponseDto findByMapDetail(Long mapNo) {
        return queryFactory
                .select(Projections.fields(
                        MapDetailSubResponseDto.class,
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
    }

    public List<MapImgResponseDto> findByMapImg(Long mapNo) {
        return queryFactory
                .select(Projections.fields(
                        MapImgResponseDto.class,
                        mapImg.mapImgUrl.as("mapImgUrl")
                )).distinct()
                .from(mapImg)
                .join(mapImg.map, map)
                .where(mapImg.map.mapNo.eq(mapNo))
                .fetch();
    }

    public List<ReviewResponseDto> findByMapReview(Long mapNo, int pagenum, int pagesize) {
        return queryFactory
                .select(Projections.fields(
                        ReviewResponseDto.class,
                        mapReview.mapReviewNo.as("reviewNo"),
                        mapReview.member.nick,
                        mapReview.content,
                        dog.image.as("image"),
                        mapReview.star
                ))
                .from(mapReview)
                .leftJoin(dog)
                .on(dog.member.memberNo.eq(mapReview.member.memberNo))
                .where(mapReview.map.mapNo.eq(mapNo))
                .limit(pagesize)
                .offset((long) pagenum * pagesize)
                .fetch();
    }
}
