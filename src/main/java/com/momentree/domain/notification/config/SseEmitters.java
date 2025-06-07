package com.momentree.domain.notification.config;

import com.momentree.global.exception.BaseException;
import com.momentree.global.exception.ErrorCode;
import org.apache.catalina.connector.ClientAbortException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class SseEmitters {
    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    // 새로운 SSE 연결을 추가하고 관련 콜백을 설정하는 메서드
    public SseEmitter add(SseEmitter emitter) {
        this.emitters.add(emitter);

        // 클라이언트와의 연결이 완료되면 컬렉션에서 제거하는 콜백
        emitter.onCompletion(() -> {
            this.emitters.remove(emitter);
        });

        // 연결이 타임아웃되면 완료 처리하는 콜백
        emitter.onTimeout(() -> {
            emitter.complete();
        });

        return emitter;
    }

    // 데이터 없이 이벤트 이름만으로 알림을 보내는 간편 메서드
    public void noti(String eventName) {
        noti(eventName, new HashMap<>()); // 빈 Map으로 알림 전송
    }

    // 모든 연결된 클라이언트들에게 이벤트를 전송하는 메서드
    public void noti(String eventName, Map<String, Object> data) {
        // 모든 emitter에 대해 반복하며 이벤트 전송
        emitters.forEach(emitter -> {
            try {
                emitter.send(
                        SseEmitter.event()
                                .name(eventName)    // 이벤트 이름 설정
                                .data(data)         // 전송할 데이터 설정
                );
            } catch (ClientAbortException e) {
                // 클라이언트가 연결을 강제로 종료한 경우 무시
            } catch (IOException e) {
                throw new BaseException(ErrorCode.FAILED_TO_CONNECT_SERVER);
            }
        });
    }
}
