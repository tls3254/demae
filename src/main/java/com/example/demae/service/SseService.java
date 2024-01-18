package com.example.demae.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class SseService {
	private static final long DEFAULT_TIMEOUT = 30 * 60 * 1000;
	private final Map<String, SseEmitter> userEmitters = new ConcurrentHashMap<>();

	public SseEmitter createConnect(Long userId) {
		String id = String.valueOf(userId);
		if(userEmitters.containsKey(String.valueOf(id))) {
			SseEmitter sseEmitter = userEmitters.get(id);
			userEmitters.remove(sseEmitter);
		}

		SseEmitter emitter = new SseEmitter(DEFAULT_TIMEOUT);
		userEmitters.put(id, emitter);
		emitter.onCompletion(() -> userEmitters.remove(id, emitter));
		emitter.onTimeout(() -> userEmitters.remove(id, emitter));

		return emitter;
	}

	public SseEmitter getUserEmitters(String userId) {
		return userEmitters.get(String.valueOf(userId));
	}

	public void deleteEmitters(SseEmitter emitter) {
		userEmitters.remove(emitter);
	}
}
