package com.example.demae.domain.cart.controller;

import com.example.demae.domain.cart.dto.request.CartRequestDto;
import com.example.demae.domain.cart.dto.request.OrderRequestDto;
import com.example.demae.domain.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/carts")
public class CartController {

	private final CartService cartService;

	@PostMapping
	public ResponseEntity<String> createCart(@RequestBody OrderRequestDto orderRequestDto,
										      @AuthenticationPrincipal UserDetails userDetails) {
		cartService.createCart(orderRequestDto, userDetails.getUsername());
		return ResponseEntity.ok("ok");
	}

	@PostMapping("/confirm")
	public ResponseEntity<String> shoppingCart(@RequestBody CartRequestDto cartRequestDto,
										        @AuthenticationPrincipal UserDetails userDetails) {
		cartService.addOrderItem(cartRequestDto, userDetails.getUsername());
		return ResponseEntity.ok("ok");
	}

	@DeleteMapping("/{cartId}")
	public ResponseEntity<String> deleteCart(@PathVariable Long cartId,
											  @AuthenticationPrincipal UserDetails userDetails) {
		cartService.deleteCart(cartId, userDetails.getUsername());
		return ResponseEntity.ok("ok");
	}
}