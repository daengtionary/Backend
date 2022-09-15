package com.sparta.daengtionary.websocket.chatdto.response;

import lombok.Builder;
import lombok.Getter;

public class ChatRoomResponseDto {

    @Getter
    public static class Create {
        private Long chatRoomNo;

        public Create() {

        }

        @Builder
        public Create(Long chatRoomNo) {
            this.chatRoomNo = chatRoomNo;
        }
    }
}