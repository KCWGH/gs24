package com.gs24.website.service;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
public class SseServiceImple implements SseService {
	private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();

	@Override
	public SseEmitter subscribe(String username) {
		// 기존 emitter 제거
		SseEmitter existingEmitter = emitters.remove(username);
		if (existingEmitter != null) {
			try {
				existingEmitter.complete();
			} catch (Exception e) {
			}
		}

		// 새로운 emitter 생성
		SseEmitter emitter = new SseEmitter(30 * 1000L);
		emitters.put(username, emitter);

		emitter.onTimeout(() -> {
			emitters.remove(username);
			try {
				emitter.send(SseEmitter.event().data("연결이 타임아웃되었습니다. 다시 시도해 주세요."));
			} catch (Exception e) {
			}
		});

		return emitter;
	}

	@Override
	public void sendNotification(String username, String message) {
	    SseEmitter emitter = emitters.get(username);
	    if (emitter != null) {
	        try {
	            // 메시지를 UTF-8로 인코딩하여 바이트 배열로 전송
	            emitter.send(SseEmitter.event().data(message.getBytes(StandardCharsets.UTF_8)));
	        } catch (Exception e) {
	            emitters.remove(username);
	        }
	    }
	}

}
