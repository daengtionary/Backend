package com.sparta.daengtionary.category.chat.repository;

import com.sparta.daengtionary.aop.redis.RedisSubscriber;
import com.sparta.daengtionary.category.chat.domain.ChatRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Repository
public class ChatRoomRedisRepository {
    // 채팅방(topic)에 발행되는 메시지를 처리할 Listner
    private final RedisMessageListenerContainer redisMessageListener;
    // 구독 처리 서비스
    private final RedisSubscriber redisSubscriber;
    // Redis
    private static final String CHAT_ROOMS = "CHAT_ROOM";
    private final RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String, String, ChatRoom> opsHashChatRoom;
    private final StringRedisTemplate stringRedisTemplate;
    // 채팅방의 대화 메시지를 발행하기 위한 redis topic 정보. 서버별로 채팅방에 매치되는 topic정보를 Map에 넣어 roomNo로 찾을수 있도록 한다.
    private ValueOperations<String, String> topics;

    @PostConstruct
    private void init() {
        opsHashChatRoom = redisTemplate.opsForHash();
        topics = stringRedisTemplate.opsForValue();
    }

    public List<ChatRoom> findAllRoom() {
        return opsHashChatRoom.values(CHAT_ROOMS);
    }

    public ChatRoom findRoomById(String id) {
        return opsHashChatRoom.get(CHAT_ROOMS, id);
    }

    // 채팅방 생성 : 서버간 채팅방 공유를 위해 redis hash에 저장한다.
    public void createChatRoom(ChatRoom chatRoom) {
        opsHashChatRoom.put(CHAT_ROOMS, String.valueOf(chatRoom.getRoomNo()), chatRoom);
    }

    // 채팅방 입장 : redis에 topic을 만들고 pub/sub 통신을 하기 위해 리스너를 설정한다.
    public void enterChatRoom(String roomNo) {
        if (topics.get(roomNo) == null) {
            ChannelTopic topic = new ChannelTopic(roomNo);
            redisMessageListener.addMessageListener(redisSubscriber, topic);
            topics.set(roomNo, topic.toString());
            redisTemplate.expire(roomNo, 48, TimeUnit.HOURS);
        } else {
            String topicToString = topics.get(roomNo);
            ChannelTopic topic = new ChannelTopic(topicToString);
            redisMessageListener.addMessageListener(redisSubscriber, topic);
        }
    }

    public ChannelTopic getTopic(String roomNo) {
        String topicToString = topics.get(roomNo);
        return new ChannelTopic(topicToString);
    }
}