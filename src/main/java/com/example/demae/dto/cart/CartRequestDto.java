package com.example.demae.dto.cart;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartRequestDto {
	private int menuId;
	private int quantity;
	private int storeId;
}
