package com.sparta.daengtionary.category.community.repository;

import com.sparta.daengtionary.category.community.domain.Community;
import com.sparta.daengtionary.category.community.domain.CommunityImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommunityImgRepository extends JpaRepository<CommunityImg, Long> {

    List<CommunityImg> findAllByCommunity(Community community);

}
