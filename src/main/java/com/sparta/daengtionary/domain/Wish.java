package com.sparta.daengtionary.domain;

import com.sparta.daengtionary.configration.error.CustomException;
import com.sparta.daengtionary.configration.error.ErrorCode;
import com.sparta.daengtionary.domain.community.Community;
import com.sparta.daengtionary.domain.map.Map;
import com.sparta.daengtionary.domain.trade.Trade;
import com.sparta.daengtionary.util.Timestamped;
import lombok.Builder;
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

    public Wish(Community community,Member member){
        this.community = community;
        this.member = member;
    }

    public Wish(Trade trade , Member member){
        this.trade = trade;
        this.member = member;
    }


    public void validateMember(Member member) {
        if (!this.member.equals(member)) {
            throw new CustomException(ErrorCode.MAP_WRONG_ACCESS);
        }
    }

}
