package com.example.demae.controller;

import com.example.demae.dto.order.OrderRequestDto;
import com.example.demae.dto.order.OrderStateDto;
import com.example.demae.security.UserDetailsImpl;
import com.example.demae.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {
	private final OrderService orderService;


	@GetMapping("/{orderId}")
	public String getOrder(@PathVariable Long orderId, Model model,
						   @AuthenticationPrincipal UserDetailsImpl userDetails) {
		model.addAttribute("order", orderService.getOrder(orderId, userDetails.getUser()));
		return "/admin/order/orderInfoPage";
	}

	@GetMapping
	public String getAllOrder(@AuthenticationPrincipal UserDetailsImpl userDetails,
							  Model model) {
		if(userDetails.getUser().getRole().name().equals("USER")) {
			model.addAttribute("orderAllUser",orderService.getAllOrderInfoUser(userDetails.getUser()));
			return "/user/order/orderAllInfoPageUser";
		}
		model.addAttribute("orderAll", orderService.getAllOrderInfo(userDetails.getUser()));
		return "/admin/order/orderAllInfoPage";
	}

	@PostMapping
	@ResponseBody
	public String createOrder(@RequestBody OrderRequestDto orderRequestDto,
							  @AuthenticationPrincipal UserDetailsImpl userDetails) {
		return orderService.createOrder(orderRequestDto, userDetails.getUser());
	}

	// 주문완료 컨트롤러
	@PostMapping("/{orderId}")
	@ResponseBody
	public String completeOrder(@PathVariable Long orderId,
								@RequestBody OrderStateDto orderStateDto,
								@AuthenticationPrincipal UserDetailsImpl userDetails) {
		return orderService.completeOrder(orderId, orderStateDto.getOrderState(), userDetails.getUser());
	}
}
