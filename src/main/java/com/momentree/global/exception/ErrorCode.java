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
    NOT_FOUND_COUPLE(HttpStatus.NOT_FOUND.value(), "일치하는 커플이 없습니다."),
    NOT_FOUND_CATEGORY(HttpStatus.NOT_FOUND.value(), "일치하는 카테고리가 없습니다."),
    NOT_FOUND_SCHEDULE(HttpStatus.NOT_FOUND.value(), "일치하는 스케줄이 없습니다."),
    ALREADY_CONNECTED_COUPLE(HttpStatus.BAD_REQUEST.value(), "이미 커플이 연결된 상태입니다."),
    CANNOT_CONNECT_SELF(HttpStatus.BAD_REQUEST.value(), "자기 자신과는 커플 연결을 할 수 없습니다."),

    /**
     * 400 : Validation Error
     */
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST.value(), "요청 데이터 검증 오류"),

    INVALID_JWT(HttpStatus.UNAUTHORIZED.value(), "유효하지 않은 JWT입니다."),
    INVALID_AUTHORIZATION(HttpStatus.CONFLICT.value(), "잘못된 권한입니다."),
    INVALID_SCHEDULE_DATA(HttpStatus.BAD_REQUEST.value(), "잘못된 일정 데이터가 제공되었습니다."),



    /**
     * 500 : Database, Server 오류
     */
    FAILED_TO_CONNECT_DATABASE(HttpStatus.INTERNAL_SERVER_ERROR.value(), "데이터베이스 연결에 실패하였습니다."),
    FAILED_TO_CONNECT_SERVER(HttpStatus.INTERNAL_SERVER_ERROR.value(), "서버와의 연결에 실패하였습니다."),
    UNEXPECTED_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "예상치 못한 에러가 발생했습니다."),
    FAILED_CREATE_SCHEDULE(HttpStatus.INTERNAL_SERVER_ERROR.value(), "서버 내부 오류로 인해 스케줄 생성에 실패했습니다."),
    ;

    private final int code;
    private final String message;

}
