package com.momentree.domain.notification.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class SseEmitters {
    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    public SseEmitter add(SseEmitter emitter) {
        this.emitters.add(emitter);

        // 연결 완료 시 제거
        emitter.onCompletion(() -> {
            this.emitters.remove(emitter);
        });

        // 타임아웃 시 제거
        emitter.onTimeout(() -> {
            this.emitters.remove(emitter);
            emitter.complete();
        });

        // 에러 발생 시 제거
        emitter.onError((throwable) -> {
            this.emitters.remove(emitter);
        });

        return emitter;
    }

    public void noti(String eventName, Map<String, Object> data) {
        // 안전한 전송을 위한 방어 코드
        emitters.removeIf(emitter -> {
            try {
                emitter.send(SseEmitter.event()
                        .name(eventName)
                        .data(data));
                return false; // 전송 성공 시 유지
            } catch (IOException | IllegalStateException e) {
                return true;  // 전송 실패 시 제거
            }
        });
    }
}

