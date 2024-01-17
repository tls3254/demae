package com.example.demae.controller;

import com.example.demae.dto.order.OrderRequestDto;
import com.example.demae.dto.order.OrderStateDto;
import com.example.demae.security.UserDetailsImpl;
import com.example.demae.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {
	private final OrderService orderService;
	private final CopyOnWriteArrayList<SseEmitter> emitters = new CopyOnWriteArrayList<>();

	@GetMapping("/{orderId}")
	public String getOrder(@PathVariable Long orderId, Model model,
						   @AuthenticationPrincipal UserDetailsImpl userDetails) {
		model.addAttribute("order", orderService.getOrder(orderId, userDetails.getUser()));
		return "orderInfoPage";
	}

	@GetMapping
	public String getAllOrder(@AuthenticationPrincipal UserDetailsImpl userDetails,
							  Model model) {
		if(userDetails.getUser().getRole().name().equals("USER")) {
			model.addAttribute("orderAllUser",orderService.getAllOrderInfoUser(userDetails.getUser()));
			return "orderAllInfoPageUser";
		}
		model.addAttribute("orderAll", orderService.getAllOrderInfo(userDetails.getUser()));
		return "orderAllInfoPage";
	}

	@PostMapping
	@ResponseBody
	public String createOrder(@RequestBody OrderRequestDto orderRequestDto,
							  @AuthenticationPrincipal UserDetailsImpl userDetails) {
		return orderService.createOrder(orderRequestDto, userDetails.getUser());
	}

	@PostMapping("/{orderId}")
	@ResponseBody
	public String completeOrder(@PathVariable Long orderId,
								@RequestBody OrderStateDto orderStateDto,
								@AuthenticationPrincipal UserDetailsImpl userDetails) {
		// SSE 이벤트 전송
		emitters.forEach(emitter -> {
			try {
				emitter.send(SseEmitter.event().data("{\"status\":\"ok\"}", MediaType.APPLICATION_JSON));
			} catch (IOException e) {
				e.printStackTrace();
			}
		});

		// 필요에 따라 emitters를 초기화하거나 관리할 수 있습니다.
		emitters.clear();
		return orderService.completeOrder(orderId, orderStateDto.getOrderState(), userDetails.getUser());
	}
}
