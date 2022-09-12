package com.sparta.daengtionary.websocket.sse;


import com.sparta.daengtionary.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRequestDto {
    // 받는사람 userid
    private Member receiver;


}
