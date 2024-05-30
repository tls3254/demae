package com.example.demae.domain.cart.dto.response;

import com.example.demae.domain.cart.entity.Cart;
import com.example.demae.domain.cart.entity.CartState;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class OrderAllResponseDto {
	private Long id;
	private Long userId;
	private LocalDateTime date;
	private CartState state;
	private int totalPrice;

	public OrderAllResponseDto(Cart cart) {
		this.id = cart.getCartId();
		this.userId = cart.getUser().getUserId();
		this.date = cart.getModifiedAt();
		this.state = cart.getCartState();
	}
}
