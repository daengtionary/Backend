package com.sparta.daengtionary.aop.supportrepository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.daengtionary.category.community.dto.response.CommunityDetatilResponseDto;
import com.sparta.daengtionary.category.community.dto.response.CommunityReviewResponseDto;
import com.sparta.daengtionary.category.recommend.domain.Map;
import com.sparta.daengtionary.category.recommend.dto.response.ImgResponseDto;
import com.sparta.daengtionary.category.recommend.dto.response.MapDetailSubResponseDto;
import com.sparta.daengtionary.category.recommend.dto.response.ReviewResponseDto;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.sparta.daengtionary.category.community.domain.QCommunity.community;
import static com.sparta.daengtionary.category.community.domain.QCommunityImg.communityImg1;
import static com.sparta.daengtionary.category.community.domain.QCommunityReview.communityReview;
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
                ))
                .from(map)
                .join(map.member, member)
                .where(map.mapNo.eq(mapNo))
                .fetchOne();
    }

    public List<ImgResponseDto> findByMapImg(Long mapNo) {
        return queryFactory
                .select(Projections.fields(
                        ImgResponseDto.class,
                        mapImg.mapImgUrl.as("mapImgUrl")
                ))
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

    public CommunityDetatilResponseDto findByCommunity(Long communityNo) {
        return queryFactory
                .select(Projections.fields(
                        CommunityDetatilResponseDto.class,
                        community.communityNo,
                        community.member.nick,
                        community.title,
                        community.category,
                        dog.breed.as("breed"),
                        dog.image,
                        community.content,
                        community.view,
                        community.createdAt,
                        community.modifiedAt
                ))
                .from(community)
                .leftJoin(member)
                .on(community.member.memberNo.eq(member.memberNo))
                .leftJoin(dog)
                .on(community.member.eq(dog.member))
                .where(community.communityNo.eq(communityNo))
                .fetchOne();
    }

    public List<ImgResponseDto> findByCommunityImg(Long communityNo) {
        return queryFactory
                .select(Projections.fields(
                        ImgResponseDto.class,
                        communityImg1.communityImg.as("mapImgUrl")
                ))
                .from(communityImg1)
                .join(communityImg1.community, community)
                .where(communityImg1.community.communityNo.eq(communityNo))
                .fetch();
    }

    public List<CommunityReviewResponseDto> findByCommunityReview(Long communityNo, int pagenum, int pagesize) {
        return queryFactory
                .select(Projections.fields(
                        CommunityReviewResponseDto.class,
                        communityReview.communityReviewNo.as("reviewNo"),
                        member.nick,
                        communityReview.content,
                        dog.image
                ))
                .from(communityReview)
                .join(communityReview.member, member)
                .join(communityReview.community, community)
                .leftJoin(dog)
                .on(dog.member.memberNo.eq(communityReview.member.memberNo))
                .where(communityReview.community.communityNo.eq(communityNo))
                .limit(pagesize)
                .offset((long) pagenum * pagesize)
                .fetch();
    }

}
