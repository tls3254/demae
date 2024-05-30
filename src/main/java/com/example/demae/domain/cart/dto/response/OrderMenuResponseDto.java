package com.example.demae.domain.cart.dto.response;

import com.example.demae.domain.cart.entity.OrderItem;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderMenuResponseDto {
	private Long cartId;
	private int price;
	private int quantity;

	public OrderMenuResponseDto(OrderItem orderList) {
		this.cartId = orderList.getCart().getCartId();
		this.price = orderList.getPrice();
		this.quantity = orderList.getQuantity();
	}
}
