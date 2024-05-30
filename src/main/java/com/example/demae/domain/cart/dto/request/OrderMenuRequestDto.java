package com.example.demae.domain.cart.dto.request;

import com.example.demae.domain.store.entity.Store;
import com.example.demae.domain.user.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderMenuRequestDto {
	private Long MenuId;
	private int menuPrice;
	private int menuQuantity;
	private Store store;
	private User user;
}
