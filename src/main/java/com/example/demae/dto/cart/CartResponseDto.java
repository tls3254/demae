package com.example.demae.dto.cart;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartResponseDto {
	private Long id;
	private String name;
	private int price;
	private int quantity;
	private Long storeId;

	public CartResponseDto(Menu menu, int quantity){
		this.id = menu.getId();
		this.name =menu.getName();
		this.price = menu.getPrice();
		this.storeId = menu.getStore().getId();
		this.quantity = quantity;
	}
}
