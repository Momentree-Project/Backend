package com.momentree.global.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"code", "message", "data"})
public class BaseResponse<T> {
    private final int code;
    private final String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    // 요청에 성공한 경우
    public BaseResponse(T data) {
        this.code = ErrorCode.SUCCESS.getCode();
        this.message = ErrorCode.SUCCESS.getMessage();
        this.data = data;
    }

    // 요청에 실패한 경우
    public BaseResponse(ErrorCode status) {
        this.code = status.getCode();
        this.message = status.getMessage();
    }

    //validation 오류
    public BaseResponse(ErrorCode status, String validMessage) {
        this.code = status.getCode();
        this.message = validMessage != null && !validMessage.isEmpty() ? validMessage : status.getMessage();
    }

}
