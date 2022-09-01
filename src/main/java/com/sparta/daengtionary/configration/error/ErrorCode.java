package com.sparta.daengtionary.configration.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    OK(HttpStatus.OK,"200","true"),

    //문자열 체크
    NOT_VALIDCONTENT(HttpStatus.BAD_REQUEST,"400","유효하지 않는 내용입니다."),
    NOT_VALIDURL(HttpStatus.BAD_REQUEST,"400","유효하지 않는 URL 입니다."),

    //회원가입


    //TOKEN


    //로그인
    LOGIN_NOT_FOUNT_MEMBERID(HttpStatus.NOT_FOUND, "404", "해당 아이디를 찾을 수 없습니다"),
    //회원탈퇴

    //기타
    NOT_FOUND_USER_INFO(HttpStatus.NOT_FOUND, "404", "해당 유저가 존재하지 않습니다"),


    //게시글
    MAP_NOT_FOUND(HttpStatus.NOT_FOUND,"404","해당 게시물을 찾을 수 없습니다."),
    MAP_UPDATE_WRONG_ACCESS(HttpStatus.BAD_REQUEST,"400","본인의 게시물만 수정할 수 있습니다."),
    MAP_DELETE_WRONG_ACCESS(HttpStatus.BAD_REQUEST,"400","본인의 게시물만 삭제할 수 있습니다."),
    MAP_WRONG_INPUT(HttpStatus.BAD_REQUEST,"400","비어있는 항목을 채워주세요"),

    //댓글

    //챗팅

    //이미지
    WRONG_INPUT_IMAGE(HttpStatus.BAD_REQUEST,"400","이미지는 반드시 있어야 됩니다."),
    IMAGE_UPLOAD_ERROR(HttpStatus.BAD_REQUEST,"400","이미지 업로드에 실패했습니다"),
    WRONG_IMAGE_FORMAT(HttpStatus.BAD_REQUEST,"400","지원하지 않는 파일 형식입니다.");



    private final HttpStatus status;
    private final String errorCode;
    private final String errorMessage;

}
