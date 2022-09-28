package com.sparta.daengtionary.category.wish.domain;

import com.sparta.daengtionary.aop.exception.CustomException;
import com.sparta.daengtionary.aop.exception.ErrorCode;
import com.sparta.daengtionary.aop.util.Timestamped;
import com.sparta.daengtionary.category.community.domain.Community;
import com.sparta.daengtionary.category.member.domain.Member;
import com.sparta.daengtionary.category.recommend.domain.Map;
import com.sparta.daengtionary.category.trade.domain.Trade;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Wish extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wishNo;

    @JoinColumn(name = "memberNo", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @JoinColumn(name = "mapNo")
    @ManyToOne(fetch = FetchType.LAZY)
    private Map map;

    @JoinColumn(name = "tradeNo")
    @ManyToOne(fetch = FetchType.LAZY)
    private Trade trade;

    @JoinColumn(name = "communityNo")
    @ManyToOne(fetch = FetchType.LAZY)
    private Community community;

    public Wish(Map map, Member member) {
        this.member = member;
        this.map = map;
    }

    public Wish(Community community, Member member) {
        this.community = community;
        this.member = member;
    }

    public Wish(Trade trade, Member member) {
        this.trade = trade;
        this.member = member;
    }


    public void validateMember(Member member) {
        if (!this.member.equals(member)) {
            throw new CustomException(ErrorCode.MAP_WRONG_ACCESS);
        }
    }

}
