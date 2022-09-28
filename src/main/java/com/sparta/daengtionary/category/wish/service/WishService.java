package com.sparta.daengtionary.category.wish.service;

import com.sparta.daengtionary.aop.dto.ResponseBodyDto;
import com.sparta.daengtionary.aop.exception.CustomException;
import com.sparta.daengtionary.aop.exception.ErrorCode;
import com.sparta.daengtionary.aop.jwt.TokenProvider;
import com.sparta.daengtionary.category.community.domain.Community;
import com.sparta.daengtionary.category.community.repository.CommunityRepository;
import com.sparta.daengtionary.category.member.domain.Member;
import com.sparta.daengtionary.category.recommend.domain.Map;
import com.sparta.daengtionary.category.recommend.repository.MapRepository;
import com.sparta.daengtionary.category.trade.domain.Trade;
import com.sparta.daengtionary.category.trade.repository.TradeRepository;
import com.sparta.daengtionary.category.wish.domain.Wish;
import com.sparta.daengtionary.category.wish.dto.WishResponseDto;
import com.sparta.daengtionary.category.wish.repository.WishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WishService {

    private final TokenProvider tokenProvider;
    private final ResponseBodyDto responseBodyDto;
    private final WishRepository wishRepository;
    private final MapRepository mapRepository;
    private final CommunityRepository communityRepository;
    private final TradeRepository tradeRepository;

    @Transactional
    public ResponseEntity<?> toggleWishByMap(Long requestDto) {
        Member member = tokenProvider.getMemberFromAuthentication();
        Map map = (Map) isPresentPost(requestDto, "map");
        Wish wish = isPresentWishByPost(map, member, "map");

        if (wish == null) {
            Wish tempWish = new Wish(map, member);
            wishRepository.save(tempWish);
            return responseBodyDto.success(WishResponseDto.builder()
                    .build(), "좋아요가 추가되었습니다.");
        } else {
            wishRepository.delete(wish);
            return responseBodyDto.success(WishResponseDto.builder()
                    .mapNo(wish.getMap().getMapNo())
                    .build(), "좋아요가 삭제되었습니다.");
        }
    }

    @Transactional
    public ResponseEntity<?> toggleWishByCommunity(Long requestDto) {
        Member member = tokenProvider.getMemberFromAuthentication();
        Community com = (Community) isPresentPost(requestDto, "com");
        Wish wish = isPresentWishByPost(com, member, "com");
        if (wish == null) {
            Wish tempWish = new Wish(com, member);
            wishRepository.save(tempWish);
            return responseBodyDto.success(WishResponseDto.builder()
                    .build(), "좋아요가 추가되었습니다.");
        } else {
            wishRepository.delete(wish);
            return responseBodyDto.success(WishResponseDto.builder()
                    .comNo(wish.getCommunity().getCommunityNo())
                    .build(), "좋아요가 삭제되었습니다.");
        }
    }

    @Transactional
    public ResponseEntity<?> toggleWishByTrade(Long requestDto) {
        Member member = tokenProvider.getMemberFromAuthentication();
        Trade trade = (Trade) isPresentPost(requestDto, "trade");
        Wish wish = isPresentWishByPost(trade, member, "trade");
        if (wish == null) {
            Wish tempWish = new Wish(trade, member);
            wishRepository.save(tempWish);
            return responseBodyDto.success(WishResponseDto.builder()
                    .build(), "좋아요가 추가되었습니다.");
        } else {
            wishRepository.delete(wish);
            return responseBodyDto.success(WishResponseDto.builder()
                    .tradeNo(wish.getTrade().getTradeNo())
                    .build(), "좋아요가 삭제되었습니다.");
        }
    }


    @Transactional(readOnly = true)
    public Object isPresentPost(Long requestNo, String postMsg) {
        if (postMsg.equals("map")) {
            return mapRepository.findById(requestNo).orElseThrow(
                    () -> new CustomException(ErrorCode.MAP_NOT_FOUND)
            );
        }
        if (postMsg.equals("com")) {
            return communityRepository.findById(requestNo).orElseThrow(
                    () -> new CustomException(ErrorCode.MAP_NOT_FOUND)
            );
        }
        if (postMsg.equals("trade")) {
            return tradeRepository.findById(requestNo).orElseThrow(
                    () -> new CustomException(ErrorCode.MAP_NOT_FOUND)
            );
        }
        throw new CustomException(ErrorCode.MAP_NOT_FOUND);
    }

    @Transactional(readOnly = true)
    public Wish isPresentWishByPost(Object post, Member member, String msg) {
        if (msg.equals("map")) {
            return wishRepository.findByMapAndMember(post, member);
        }
        if (msg.equals("com")) {
            return wishRepository.findByCommunityAndMember(post, member);
        }
        if (msg.equals("trade")) {
            return wishRepository.findByTradeAndMember(post, member);
        }

        throw new CustomException(ErrorCode.MAP_NOT_FOUND);
    }


}
