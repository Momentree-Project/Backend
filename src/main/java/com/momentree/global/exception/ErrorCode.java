package com.momentree.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    /**
     * 200 : 요청 성공
     */
    SUCCESS(HttpStatus.OK.value(), "요청 성공"),
    NO_CONTENT(HttpStatus.NO_CONTENT.value(), "요청 성공 (no content)"),
    CREATED(HttpStatus.CREATED.value(), "요청 성공 (created)"),

    /**
     * 400 : Request, Response 오류
     */
    NOT_FOUND_USER(HttpStatus.NOT_FOUND.value(), "일치하는 유저가 없습니다."),

    /**
     * 400 : Validation Error
     */
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST.value(), "요청 데이터 검증 오류"),

    INVALID_JWT(HttpStatus.UNAUTHORIZED.value(), "유효하지 않은 JWT입니다."),
    INVALID_AUTHORIZATION(HttpStatus.CONFLICT.value(), "잘못된 권한입니다."),


    /**
     * 500 : Database, Server 오류
     */
    FAILED_TO_CONNECT_DATABASE(HttpStatus.INTERNAL_SERVER_ERROR.value(), "데이터베이스 연결에 실패하였습니다."),
    FAILED_TO_CONNECT_SERVER(HttpStatus.INTERNAL_SERVER_ERROR.value(), "서버와의 연결에 실패하였습니다."),
    UNEXPECTED_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "예상치 못한 에러가 발생했습니다.");

    private final int code;
    private final String message;

}
