package com.sparta.daengtionary.repository;

import com.sparta.daengtionary.domain.Community;
import com.sparta.daengtionary.domain.CommunityImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommunityImgRepository extends JpaRepository<CommunityImg, Long> {

    List<CommunityImg> findAllByCommunity(Community community);

}
