package com.example.demae.domain.cart.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class OrderResponseDto {
	private List<OrderMenuResponseDto> orderList = new ArrayList<>();
	private int totalPrice;

	public void addItem(OrderMenuResponseDto orderDto) {
		this.orderList.add(orderDto);
	}

	public void addToTotalPrice(int price) {
		this.totalPrice += price;
	}
}
