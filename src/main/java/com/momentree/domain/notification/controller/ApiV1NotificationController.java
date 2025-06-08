package com.momentree.domain.notification.controller;

import com.momentree.domain.notification.config.SseEmitters;
import com.momentree.global.exception.BaseException;
import com.momentree.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class ApiV1NotificationController {
    private final SseEmitters sseEmitters;

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
