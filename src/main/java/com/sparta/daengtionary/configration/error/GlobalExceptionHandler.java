package com.sparta.daengtionary.configration.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {CustomException.class})
    public ResponseEntity<Object> handleApiRequestException(CustomException ex) {
        HttpStatus status = ex.getErrorCode().getStatus();
        String errerCode = ex.getErrorCode().getErrorMessage();
        String errerMSG = ex.getErrorCode().getErrorMessage();
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setStatus(status);
        exceptionResponse.setErrorCode(errerCode);
        exceptionResponse.setErrorMessage(errerMSG);


        System.out.println("ERR : " + status + "," + errerCode + "," + errerMSG);

        return new ResponseEntity<>(
                exceptionResponse,
                ex.getErrorCode().getStatus()
        );
    }

}
