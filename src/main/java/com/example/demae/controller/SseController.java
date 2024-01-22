package com.example.demae.controller;

import com.example.demae.dto.store.StoreResponseDto;
import com.example.demae.entity.Order;
import com.example.demae.entity.Store;
import com.example.demae.repository.StoreRepository;
import com.example.demae.security.UserDetailsImpl;
import com.example.demae.service.OrderService;
import com.example.demae.service.SseService;
import com.example.demae.service.StoreService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/sse")
public class SseController {
	private static final long DEFAULT_TIMEOUT = 30 * 60 * 1000;
	private final OrderService orderService;
	//	private final Map<String, SseEmitter> userEmitters = new ConcurrentHashMap<>();
	private final SseService sseService;
	private final StoreService storeService;
	@GetMapping(value = "/connect", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public SseEmitter connect(@AuthenticationPrincipal UserDetailsImpl userDetails) {
		SseEmitter emitter = sseService.createConnect(userDetails.getUser().getId());
		return emitter;
	}

	//  사장님이 주문 확인 버튼을 누르면 주문 확인 메시지 나감
	@GetMapping(value = "/{orderId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public void completeOrder(@PathVariable Long orderId,
							  @AuthenticationPrincipal UserDetailsImpl userDetails) {

		Order order = orderService.completeOrder(orderId, userDetails.getUser());
		List<SseEmitter> emitters = sseService.findUserAndStore(orderId,userDetails,order);
		String state = order.getState().toString().equals("CONFIRM") ? "주문이 완료 되었습니다." : "배달이 완료 되었습니다.";
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
			// 에러 처리
		}
	}

	@GetMapping(value = "/user/{orderId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public void userRequestOrder(@PathVariable Long orderId,
								 @AuthenticationPrincipal UserDetailsImpl userDetails) {
		Order orderForUser = orderService.getOrderForUser(orderId, userDetails.getUser());
		Store storeForUser = storeService.findStoreForUser(orderForUser.getStore().getId());
		SseEmitter emitter = sseService.getUserEmitters(String.valueOf(storeForUser.getUser().getId()));
		String jsonData = "{\"message\": \"주문이 접수되었습니다.!!\"}";
		try {
			emitter.send(SseEmitter.event()
					.data(jsonData, MediaType.TEXT_EVENT_STREAM));
			emitter.complete();
		}catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

//	@GetMapping(value = "/end/{orderId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
//	public void endRequestOrder(@PathVariable Long orderId,
//								@AuthenticationPrincipal UserDetailsImpl userDetails){
//
//		Order order = orderService.endOrder(orderId, userDetails.getUser()); //order 에 주문테이블하고 사장님 아이디를 넘겨주어서
//		List<SseEmitter> emitters = sseService.findUserAndStore(orderId,userDetails,order);
//		try{
//			for(SseEmitter emitter:emitters){
//				if (emitter != null) {
//					String jsonData = "{\"message\": \"배달이 완료되었습니다.!!\"}";
//					emitter.send(SseEmitter.event()
//							.data(jsonData, MediaType.TEXT_EVENT_STREAM));
//					emitter.complete();
//				}
//			}
//		}catch (IOException j){
//		}
//	}
}
