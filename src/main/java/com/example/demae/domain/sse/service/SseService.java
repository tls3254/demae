package com.example.demae.domain.sse.service;

import com.example.demae.domain.cart.entity.Cart;
import com.example.demae.domain.user.entity.User;
import com.example.demae.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class SseService {
	private static final long DEFAULT_TIMEOUT = 30 * 60 * 1000;
	private final Map<String, SseEmitter> userEmitters = new ConcurrentHashMap<>();
	private final UserService userService;

	public SseEmitter createConnect(String username) {
		User user = userService.findUser(username);
		String id = String.valueOf(user.getUserId());
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

	public List<SseEmitter> findUserAndStore(Cart cart){
		String userId = String.valueOf(cart.getUser().getUserId()); // 주문을 한 유저의 고유 ID
		String storeUserId = String.valueOf(cart.getOrderItems().get(0).getStore().getUser().getUserId());
		SseEmitter userEmitter = getUserEmitters(userId);
		SseEmitter storeEmitter = getUserEmitters(storeUserId);
		return Arrays.asList(userEmitter, storeEmitter);
	}
}
