package com.example.demae.dto.order;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class OrderRequestDto {
	private List<OrderMenuRequestDto> cartList;
	private int totalPrice;
}
