package com.sparta.daengtionary.category.community.repository;

import com.sparta.daengtionary.category.community.domain.Community;
import com.sparta.daengtionary.category.community.domain.CommunityReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommunityReviewRepository extends JpaRepository<CommunityReview, Long> {
    List<CommunityReview> findAllByCommunityOrderByCreatedAtDesc(Community community);

    List<CommunityReview> findAllByCommunity(Community community);

}
