package com.example.demae.domain.cart.dto.response;

import com.example.demae.domain.menu.entity.Menu;
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
	private Long userId;

	public CartResponseDto(Menu menu, int quantity,Long userId){
		this.id = menu.getMenuId();
		this.name =menu.getMenuName();
		this.price = menu.getMenuPrice();
		this.storeId = menu.getStore().getStoreId();
		this.quantity = quantity;
		this.userId =userId;
	}
}
