package com.example.demae.dto.order;

import com.example.demae.entity.Order;
import com.example.demae.entity.OrderList;
import com.example.demae.enums.OrderState;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
public class OrderAllResponseDto {
	private Long id;
	private String storeName;
	private Long userId;
	private LocalDateTime date;
	private OrderState state;
	private int totalPrice;

	public OrderAllResponseDto(Order order) {
		this.id = order.getId();
		this.storeName = order.getStore().getName();
		this.userId = order.getUser().getId();
		this.date = order.getCreateAt();
		this.state = order.getState();
	}
}
