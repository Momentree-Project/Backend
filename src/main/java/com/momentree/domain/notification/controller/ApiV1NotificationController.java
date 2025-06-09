package com.momentree.domain.notification.controller;

import com.momentree.domain.auth.oauth2.CustomOAuth2User;
import com.momentree.domain.notification.config.SseEmitters;
import com.momentree.domain.notification.dto.request.NotificationRequest;
import com.momentree.domain.notification.dto.response.NotificationResponse;
import com.momentree.domain.notification.service.NotificationService;
import com.momentree.global.exception.BaseException;
import com.momentree.global.exception.BaseResponse;
import com.momentree.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class ApiV1NotificationController {
    private final SseEmitters sseEmitters;
    private final NotificationService notificationService;

    @PatchMapping
    public BaseResponse<Page<NotificationResponse>> patchAllNotification (
            @AuthenticationPrincipal CustomOAuth2User loginUser,
            @PageableDefault(size = 5) Pageable pageable
    ) {
        return new BaseResponse<>(notificationService.patchAllNotification(loginUser, pageable));
    }

    @PatchMapping("/{notificationId}")
    public BaseResponse<NotificationResponse> patchNotification (
            @AuthenticationPrincipal CustomOAuth2User loginUser,
            @PathVariable Long notificationId
    ) {
        return new BaseResponse<>(notificationService.patchNotification(loginUser, notificationId));
    }

    // 가장 최근 알림 조회
    @GetMapping("/latest")
    public BaseResponse<NotificationResponse> getLatestNotification (
            @AuthenticationPrincipal CustomOAuth2User loginUser
    ) {
        return new BaseResponse<>(notificationService.getLatestNotification(loginUser));
    }

    // 모든 알림 조회
    @GetMapping
    public BaseResponse<Page<NotificationResponse>> getAllNotification (
            @AuthenticationPrincipal CustomOAuth2User loginUser,
            @PageableDefault(size = 5) Pageable pageable
    ) {
        return new BaseResponse<>(notificationService.getAllNotification(loginUser, pageable));
    }

    // 알람 생성 (관리자)
    // TODO : 관리자 권한 체크 필요, 현재는 관리자가 없으므로 추후에 추가 예정
    @PostMapping
    public BaseResponse<Void> postNotification (
            @AuthenticationPrincipal CustomOAuth2User loginUser,
            @RequestBody NotificationRequest request
            ) {
        notificationService.postNotification(loginUser, request);
        return new BaseResponse<>(ErrorCode.SUCCESS);
    }

    @GetMapping(value = "/connects",
            produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<SseEmitter> connect() {
        // 새로운 SSE 연결 생성 (기본 타임아웃 30초)
        SseEmitter emitter = new SseEmitter();

        // 생성된 emitter를 컬렉션에 추가하여 관리
        sseEmitters.add(emitter);

        try {
            // 연결된 클라이언트에게 초기 연결 성공 메시지 전송
            emitter.send(SseEmitter.event()
                    .name("connect")    // 이벤트 이름을 "connect"로 지정
                    .data("connected!")); // 전송할 데이터
        } catch (IOException e) {
            throw new BaseException(ErrorCode.FAILED_TO_CONNECT_SERVER);
        }
        return ResponseEntity.ok(emitter);
    }
}
