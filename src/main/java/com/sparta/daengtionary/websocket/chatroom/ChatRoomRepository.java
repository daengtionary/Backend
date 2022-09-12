package com.sparta.daengtionary.websocket.chatroom;


import com.sparta.daengtionary.domain.Member;
import com.sparta.daengtionary.websocket.chatdto.RoomDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    @Query(value =
            "SELECT DISTINCT r.room_id AS roomId, r.acc_out AS accOut, r.req_out AS reqOut, r.acc_fixed AS accFixed, r.req_fixed AS reqFixed, " +
                    "u1.member_id AS accId, u1.nickname AS accNickname, u2.member_id AS reqId, u2.nickname AS reqNickname, " +
                    "msg.message AS message, msg.created_at AS date " +
                    "FROM chat_room r " +
                    "INNER JOIN members u1 ON r.acceptor_member_id = u1.member_id " +
                    "INNER JOIN members u2 ON r.requester_member_id = u2.member_id " +
                    "INNER JOIN chat_message msg ON (msg.room_id, msg.message_id) " +
                    "IN (SELECT room_id, MAX(message_id) FROM chat_message GROUP BY room_id) AND r.room_id = msg.room_id " +
                    "WHERE (r.acceptor_member_id = :member OR r.requester_member_id = :member) " +
                    "ORDER BY r.modify_at DESC",
            nativeQuery = true)
    List<RoomDto> findAllWith(@Param("member") Member member);

    @Query("SELECT room FROM ChatRoom room JOIN FETCH room.acceptor JOIN FETCH room.requester WHERE room.id = :roomId")
    Optional<ChatRoom> findByIdFetch(Long roomId);

    // 채팅방 찾아오기
    @Query("SELECT room FROM ChatRoom room " +
            "WHERE (room.requester = :requester AND room.acceptor = :acceptor) OR " +
            "(room.requester = :acceptor AND room.acceptor = :requester)")
    Optional<ChatRoom> findByMember(@Param("requester") Member requester, @Param("acceptor") Member acceptor);

}
