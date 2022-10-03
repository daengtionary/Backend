package com.sparta.daengtionary.category.friend.repository;

import com.sparta.daengtionary.category.friend.domain.Friend;
import com.sparta.daengtionary.category.friend.domain.FriendImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendImgRepository extends JpaRepository<FriendImg, Long> {
    List<FriendImg> findAllByFriend(Friend friend);
}