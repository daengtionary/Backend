package com.sparta.daengtionary.websocket.sse;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface NotificationRepository extends JpaRepository<Notification,Long> {
    @Query("select n from Notification n where n.receiver.memberNo = :memberid order by n.id desc")
    List<Notification> findAllByUserId(@Param("memberid") Long memberid);

    @Query("select count(n) from Notification n where n.receiver.memberNo = :memberid and n.isRead = false")
    Long countUnReadNotifications(@Param("memberid") Long memberid);

    Optional<Notification> findById(Long NotificationsId);

    void deleteAllByReceiverId(Long receiverId);
    void deleteById(Long notificationId);
}
