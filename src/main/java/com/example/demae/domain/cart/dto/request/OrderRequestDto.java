package com.example.demae.domain.cart.dto.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderRequestDto {
	private Long userId;
	private int orderItemPrice;
	private int orderItemQuantity;
	private Long storeId;
	private Long MenuId;
}
