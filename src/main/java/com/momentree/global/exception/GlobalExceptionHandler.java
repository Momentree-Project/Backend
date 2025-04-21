package com.momentree.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // HTTP 오류 처리
    @ExceptionHandler(BaseException.class)
    public BaseResponse<ErrorCode> BaseExceptionHandle(BaseException exception) {
        log.warn("BaseException. error message: {}", exception.getMessage());
        return new BaseResponse<>(exception.getStatus());
    }

    // 권한 처리
    @ExceptionHandler(AuthorizationDeniedException.class)
    public BaseResponse<ErrorCode> AuthorizationDeniedExceptionHandle(AuthorizationDeniedException exception) {
        log.warn("AuthorizationDeniedException. error message: {}", exception.getMessage());
        return new BaseResponse<>(ErrorCode.INVALID_AUTHORIZATION);
    }

    // validation 오류 처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseResponse<ErrorCode> ValidationExceptionHandle(MethodArgumentNotValidException exception) {
        BindingResult bindingResult = exception.getBindingResult();

        String errorDetails = bindingResult.getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));

        log.warn("Validation failed: {}", errorDetails);

        return new BaseResponse<>(ErrorCode.VALIDATION_ERROR, errorDetails);
    }

    @ExceptionHandler(Exception.class)
    public BaseResponse<ErrorCode> ExceptionHandle(Exception exception) {
        log.error("Exception has occured. ", exception);
        return new BaseResponse<>(ErrorCode.UNEXPECTED_ERROR);
    }

}
