package com.example.demae.domain.cart.controller;

import com.example.demae.domain.cart.dto.response.CartListResponseDto;
import com.example.demae.domain.cart.dto.response.OrderAllResponseDto;
import com.example.demae.domain.cart.dto.response.OrderResponseDto;
import com.example.demae.domain.user.entity.User;
import com.example.demae.domain.user.service.UserService;
import com.example.demae.domain.cart.service.CartService;
import com.example.demae.domain.sse.service.SseService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/carts")
public class CartControllerPage {
	private final CartService cartService;
	private final UserService userService;
	private final SseService sseService;

	@GetMapping
	public String getAllOrder(@AuthenticationPrincipal UserDetails userDetails,
							  Model model) {
		User user = userService.findUser(userDetails.getUsername());
		List<OrderAllResponseDto> order = cartService.getAllOrderInfo(userDetails.getUsername());
		model.addAttribute("orderAll",order);
		if(user.getUserRole().toString().equals("USER")) {
			return "order/orderAllInfoPageUser";
		}
		return "order/orderAllInfoPage";
	}

	@GetMapping("/orderItem")
	public String getOrder(@AuthenticationPrincipal UserDetails userDetails, Model model) {
		User user = userService.findUser(userDetails.getUsername());
		CartListResponseDto cart = cartService.getCart(user.getUserId());
		model.addAttribute("cartList", cart);
		return "order/cart";
	}

	@GetMapping("/{orderId}")
	public String getOrder(@PathVariable Long orderId,
						   @AuthenticationPrincipal UserDetails userDetails,
						   Model model) {
		OrderResponseDto order = cartService.getCartOne(orderId, userDetails.getUsername());
		model.addAttribute("order", order);
		return "order/orderInfoPage";
	}
}
