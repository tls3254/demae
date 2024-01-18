package com.example.demae.controller;

import com.example.demae.entity.Order;
import com.example.demae.security.UserDetailsImpl;
import com.example.demae.service.OrderService;
import com.example.demae.service.SseService;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/sse")
public class SseController {
	private final OrderService orderService;
	private final Map<String, SseEmitter> userEmitters = new ConcurrentHashMap<>();
	private final SseService sseService;

	@GetMapping(value = "/connect", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public SseEmitter connect(@AuthenticationPrincipal UserDetailsImpl userDetails) {
		// 유저가 SSE 연결을 요청할 때 사용
		String id = String.valueOf(userDetails.getUser().getId());
		SseEmitter emitter = sseService.createSse(userDetails.getUser().getId());
		userEmitters.put(id, emitter);
		return emitter;
	}

	@GetMapping(value = "/{orderId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public SseEmitter completeOrder(@PathVariable Long orderId,
									@AuthenticationPrincipal UserDetailsImpl userDetails) {

		Order order = orderService.completeOrder(orderId, userDetails.getUser());
		String userId = String.valueOf(order.getUser().getId()); // 주문을 한 유저의 고유 ID
		SseEmitter emitter = userEmitters.get(userId);
		try {
			if (emitter != null) {
				emitter.send("주문이 확인되었습니다.!!", MediaType.TEXT_EVENT_STREAM);
			}
		} catch (IOException e) {
			// 에러 처리
		}
		// 필요에 따라 emitters를 초기화하거나 관리할 수 있습니다.
		return emitter;
	}
}
