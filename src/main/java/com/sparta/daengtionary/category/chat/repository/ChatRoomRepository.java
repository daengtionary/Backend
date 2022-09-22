package com.sparta.daengtionary.category.chat.repository;

import com.sparta.daengtionary.category.chat.domain.ChatRoom;
import com.sparta.daengtionary.category.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    @Query("SELECT room FROM ChatRoom room " +
            "WHERE (room.creator = :creator AND room.target = :target) OR " +
            "(room.creator = :target AND room.target = :creator)")
    Optional<ChatRoom> findByCreatorAndTarget(@Param("creator") Member creator, @Param("target") Member target);

    @Query("SELECT room FROM ChatRoom room WHERE room.creator = :member OR room.target = :member")
    List<ChatRoom> findByCreatorOrTarget(Member member);
}