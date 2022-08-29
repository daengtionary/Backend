package com.sparta.daengtionary.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
public class ResponseBodyDto {
    private boolean success;
    private Object data;
    private Error error;
    public static ResponseBodyDto success(Object data){
        return new ResponseBodyDto(true,data,null);
    }
    public static ResponseBodyDto fail(String code, String message){
        return new ResponseBodyDto(false,null,new Error(code, message));
    }
    @Getter
    @AllArgsConstructor
    static class Error {
        private String code;
        private String message;
    }

}