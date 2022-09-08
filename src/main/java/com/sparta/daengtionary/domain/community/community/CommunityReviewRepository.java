package com.sparta.daengtionary.domain.community.community;

import com.sparta.daengtionary.domain.community.Community;
import com.sparta.daengtionary.domain.community.CommunityReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommunityReviewRepository extends JpaRepository<CommunityReview,Long> {
    List<CommunityReview> findAllByCommunityOrderByCreatedAtDesc(Community community);
    List<CommunityReview> findAllByCommunity(Community community);

}
