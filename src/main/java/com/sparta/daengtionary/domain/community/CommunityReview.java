package com.sparta.daengtionary.domain.community;

import com.sparta.daengtionary.configration.error.CustomException;
import com.sparta.daengtionary.configration.error.ErrorCode;
import com.sparta.daengtionary.domain.Member;
import com.sparta.daengtionary.dto.request.ReviewRequestDto;
import com.sparta.daengtionary.util.Timestamped;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class CommunityReview extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long communityReviewNo;

    @JoinColumn(name = "memberNo", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @JoinColumn(name = "communityNo", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Community community;

    @Column(nullable = false)
    private String content;

    @Builder
    public CommunityReview(Member member, Community community, String content) {
        this.member = member;
        this.community = community;
        this.content = content;
    }

    public void communityReviewUpdate(ReviewRequestDto requestDto) {
        this.content = requestDto.getContent();
    }

    public void validateMember(Member member) {
        if (!this.member.equals(member)) {
            throw new CustomException(ErrorCode.MAP_WRONG_ACCESS);
        }
    }


}
