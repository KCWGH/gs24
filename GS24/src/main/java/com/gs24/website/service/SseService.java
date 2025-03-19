package com.gs24.website.service;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface SseService {
	SseEmitter subscribe(String username);

	void sendNotification(String username, String message);
}
