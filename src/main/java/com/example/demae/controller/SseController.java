package com.example.demae.controller;

import com.example.demae.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class SseController {

	private final Map<String, SseEmitter> userEmitters = new ConcurrentHashMap<>();

	@GetMapping(value = "/connect", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public SseEmitter connect(@AuthenticationPrincipal UserDetailsImpl userDetails) {
		// 유저가 SSE 연결을 요청할 때 사용
		if(userEmitters.containsKey(String.valueOf(userDetails.getUser().getId()))) {
			SseEmitter sseEmitter = userEmitters.get(String.valueOf(userDetails.getUser().getId()));
			userEmitters.remove(sseEmitter);
		}

		SseEmitter emitter = new SseEmitter();
		userEmitters.put(String.valueOf(userDetails.getUser().getId()), emitter);
		emitter.onCompletion(() -> userEmitters.remove(String.valueOf(userDetails.getUser().getId()), emitter));
		emitter.onTimeout(() -> userEmitters.remove(String.valueOf(userDetails.getUser().getId()), emitter));

		return emitter;
	}

}
