package com.example.demae.domain.sse.controller;

import com.example.demae.domain.cart.entity.Cart;
import com.example.demae.domain.store.entity.Store;
import com.example.demae.domain.cart.service.CartService;
import com.example.demae.domain.sse.service.SseService;
import com.example.demae.domain.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/sse")
public class SseController {
	private static final long DEFAULT_TIMEOUT = 30 * 60 * 1000;
	private final CartService cartService;
	//	private final Map<String, SseEmitter> userEmitters = new ConcurrentHashMap<>();
	private final SseService sseService;
	private final StoreService storeService;

	@GetMapping(value = "/connect", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public SseEmitter connect(@AuthenticationPrincipal UserDetails userDetails) {
		return sseService.createConnect(userDetails.getUsername());
	}


	@GetMapping(value = "/{orderId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	@ResponseBody
	public ResponseEntity<String> completeOrder(@PathVariable Long orderId,
												@AuthenticationPrincipal UserDetails userDetails) {

		Cart cart = cartService.completeOrder(orderId, userDetails.getUsername());
		List<SseEmitter> emitters = sseService.findUserAndStore(cart);
		String state = cart.getCartState().toString().equals("CONFIRM") ? "주문이 완료 되었습니다." : "배달이 완료 되었습니다.";
		try {
			for (SseEmitter emitter : emitters) {
				if (emitter != null) {
					String jsonData = "{\"message\": \""+ state +"\"}";
					emitter.send(SseEmitter.event()
							.data(jsonData, MediaType.TEXT_EVENT_STREAM));
					emitter.complete();
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>("ok", HttpStatus.OK);
	}

	@GetMapping( "/user/{orderId}")
	@ResponseBody
	public ResponseEntity<String> userRequestOrder(@PathVariable Long orderId,
												   @AuthenticationPrincipal UserDetails userDetails) {

		Cart orderForUser = cartService.getOrderForUser(orderId, userDetails.getUsername());
		Store storeForUser = storeService.findStore(orderForUser.getOrderItems().get(0).getStore().getStoreId());
		SseEmitter emitter = sseService.getUserEmitters(String.valueOf(storeForUser.getUser().getUserId()));
		String jsonData = "{\"message\": \"주문이 접수되었습니다.!!\"}";
		try {
			emitter.send(SseEmitter.event()
					.data(jsonData, MediaType.TEXT_EVENT_STREAM));
			emitter.complete();
		}catch (IOException e) {
			throw new RuntimeException(e);
		}
		return new ResponseEntity<>("ok", HttpStatus.OK);
	}
}
