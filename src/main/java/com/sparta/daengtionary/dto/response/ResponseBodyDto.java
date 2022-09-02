package com.sparta.daengtionary.dto.response;

import com.sparta.daengtionary.configration.error.ErrorCode;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class ResponseBodyDto {

    @Getter
    @Builder
    private static class Body {

        private Boolean success;
        private Object data;
        private Integer state;
        private String massage;
    }

    public ResponseEntity<?> success(Object data, String msg, HttpStatus status) {

        Body body = Body.builder()
                .success(true)
                .data(data)
                .state(status.value())
                .massage(msg)
                .build();

        return ResponseEntity.ok(body);

    }

    // 메시지만 있는 성공 응답
    public ResponseEntity<?> success(String msg) {
        return success(Collections.emptyList(), msg, HttpStatus.OK);
    }


    public ResponseEntity<?> fail(Object data, String msg, HttpStatus status) {

        Body body = Body.builder()
                .success(false)
                .data(data)
                .state(status.value())
                .massage(msg)
                .build();

        return ResponseEntity.ok(body);

    }

    // 메시지만 있는 실패 응답
    public ResponseEntity<?> fail(String msg, HttpStatus status) {
        return fail(Collections.emptyList(), msg, status);
    }

}