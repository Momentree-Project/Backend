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
    NOT_FOUND_COMMENT(HttpStatus.NOT_FOUND.value(), "일치하는 댓글이 없습니다."),
    NOT_FOUND_CATEGORY(HttpStatus.NOT_FOUND.value(), "일치하는 카테고리가 없습니다."),
    NOT_FOUND_SCHEDULE(HttpStatus.NOT_FOUND.value(), "일치하는 스케줄이 없습니다."),
    NOT_FOUND_PARTNER(HttpStatus.NOT_FOUND.value(), "연결된 파트너를 찾을 수 없습니다."),
    NOT_FOUND_POST(HttpStatus.NOT_FOUND.value(), "일치하는 게시글이 없습니다."),
    NOTIFICATION_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "일치하는 알림이 없습니다."),
    NOT_FOUND_STRATEGY_NAME(HttpStatus.NOT_FOUND.value(), "일치하는 전략패턴이 없습니다."),
    ALREADY_CONNECTED_COUPLE(HttpStatus.BAD_REQUEST.value(), "이미 커플이 연결된 상태입니다."),
    CANNOT_CONNECT_SELF(HttpStatus.BAD_REQUEST.value(), "자기 자신과는 커플 연결을 할 수 없습니다."),
    NOT_CONNECTED_COUPLE(HttpStatus.BAD_REQUEST.value(), "커플이 연결되지 않은 상태입니다."),
    POST_STATUS_NOT_PUBLISHED(HttpStatus.BAD_REQUEST.value(), "게시글 상태가 삭제된 상태입니다."),
    NOT_FOUND_IMAGE(HttpStatus.BAD_REQUEST.value(), "일치하는 이미지가 없습니다."),

    /**
     * 400 : Validation Error
     */
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST.value(), "요청 데이터 검증 오류"),
    INVALID_PARENT_COMMENT(HttpStatus.BAD_REQUEST.value(), "부모 댓글이 유효하지 않습니다."),
    CANNOT_DELETE_COMMENT_WITH_REPLIES(HttpStatus.BAD_REQUEST.value(), "답글이 있는 댓글은 삭제할 수 없습니다."),
    INVALID_JWT(HttpStatus.UNAUTHORIZED.value(), "유효하지 않은 JWT입니다."),
    INVALID_AUTHORIZATION(HttpStatus.CONFLICT.value(), "잘못된 권한입니다."),
    INVALID_SCHEDULE_DATA(HttpStatus.BAD_REQUEST.value(), "잘못된 일정 데이터가 제공되었습니다."),
    INVALID_FILE_EXTENSION(HttpStatus.BAD_REQUEST.value(), "지원하지 않는 파일 형식입니다."),
    INVALID_FILE_TYPE(HttpStatus.BAD_REQUEST.value(), "용도에 맞지 않는 FileType 입니다."),


    /**
     * 500 : Database, Server 오류
     */
    FAILED_TO_SEND_EMAIL(HttpStatus.INTERNAL_SERVER_ERROR.value(), "이메일 발송에 실패하였습니다."),
    FAILED_TO_CONNECT_DATABASE(HttpStatus.INTERNAL_SERVER_ERROR.value(), "데이터베이스 연결에 실패하였습니다."),
    FAILED_TO_CONNECT_SERVER(HttpStatus.INTERNAL_SERVER_ERROR.value(), "서버와의 연결에 실패하였습니다."),
    UNEXPECTED_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "예상치 못한 에러가 발생했습니다."),
    FAILED_CREATE_SCHEDULE(HttpStatus.INTERNAL_SERVER_ERROR.value(), "서버 내부 오류로 인해 스케줄 생성에 실패했습니다."),
    ;

    private final int code;
    private final String message;

}
