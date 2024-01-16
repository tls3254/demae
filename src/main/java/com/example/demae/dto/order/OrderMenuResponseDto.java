package com.example.demae.dto.order;

import com.example.demae.entity.OrderList;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderMenuResponseDto {
	private Long orderId;
	private String name;
	private int price;
	private int quantity;

	public OrderMenuResponseDto(OrderList orderList) {
		this.orderId = orderList.getOrder().getId();
		this.name = orderList.getMenuName();
		this.price = orderList.getPrice();
		this.quantity = orderList.getQuantity();
	}
}
