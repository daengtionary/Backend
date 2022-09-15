package com.sparta.daengtionary.repository.community;

import com.sparta.daengtionary.domain.community.Community;
import com.sparta.daengtionary.domain.community.CommunityImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommunityImgRepository extends JpaRepository<CommunityImg, Long> {

    List<CommunityImg> findAllByCommunity(Community community);

}
