package com.example.demae.controller;

import com.example.demae.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class CartController {
	private final CartService cartService;

	@PostMapping
	@ResponseBody
	public String addCart(@RequestBody CartRequestDto cartRequestDto,
						  @AuthenticationPrincipal UserDetailsImpl userDetails) {
		return cartService.addToCart(cartRequestDto, userDetails.getUser());
	}

	@DeleteMapping
	public String deleteCart(@AuthenticationPrincipal UserDetailsImpl userDetails) {
		cartService.deleteCart(userDetails.getUser().getId());
		return "cart";
	}

	@GetMapping
	public String getCart(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
		model.addAttribute("cartList", cartService.getCart(userDetails.getUser().getId()));
		return "cart";
	}

}