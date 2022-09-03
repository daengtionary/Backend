package com.sparta.daengtionary.repository.supportRepository;


import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.daengtionary.domain.Map;
import com.sparta.daengtionary.dto.response.MapDetailResponseDto;
import com.sparta.daengtionary.dto.response.MapResponseDto;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.sparta.daengtionary.domain.QMap.map;
import static com.sparta.daengtionary.domain.QMapImg.mapImg;
import static com.sparta.daengtionary.domain.QMapInfo.mapInfo1;

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
                        mapImg.mapImgUrl,
                        mapInfo1.mapInfo,
                        map.createdAt,
                        map.modifiedAt
                ))
                .from(map)
                .leftJoin(mapImg)
                .on(map.mapNo.eq(mapImg.map.mapNo))
                .leftJoin(mapInfo1).fetchJoin()
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
                .leftJoin(mapInfo1).fetchJoin()
                .on(map.mapNo.eq(mapInfo1.map.mapNo))
                .where(map.category.eq(category))
                .groupBy(map.mapNo)
                .orderBy(map.view.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(content, pageable, content.size());
    }

    public PageImpl<MapDetailResponseDto> findAllByMapDetail(String category, Pageable pageable) {
        List<MapDetailResponseDto> content = queryFactory
                .select(Projections.fields(
                        MapDetailResponseDto.class,
                        map.mapNo,
                        map.category,
                        map.title,
                        map.content,
                        map.star,
                        map.view,
                        map.address,
                        mapImg.mapImgUrl,
                        mapInfo1.mapInfo
                ))
                .from(map)
                .leftJoin(mapImg)
                .on(map.mapNo.eq(mapImg.map.mapNo))
                .leftJoin(mapInfo1)
                .on(map.mapNo.eq(mapInfo1.map.mapNo))
                .groupBy(map.mapNo)
                .orderBy(map.mapNo.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(content, pageable, content.size());
    }


    private BooleanExpression eqCategory(String category) {
        if (category.isEmpty()) return null;
        return map.category.eq(category);
    }

}
