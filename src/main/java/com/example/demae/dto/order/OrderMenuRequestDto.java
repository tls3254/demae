package com.example.demae.dto.order;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderMenuRequestDto {
	private Long id;
	private String name;
	private int price;
	private int quantity;
	private Long storeId;
}
