package com.example.demae.domain.cart.entity;

import com.example.demae.domain.menu.entity.Menu;
import com.example.demae.domain.cart.dto.request.OrderRequestDto;
import com.example.demae.domain.store.entity.Store;
import com.example.demae.global.audit.Auditable;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem extends Auditable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long orderItemId;

	@Column(nullable = false)
	private int price;

	@Column(nullable = false)
	private int quantity;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "store_id")
	private Store store;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "menu_id")
	private Menu menu;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cart_id")
	private Cart cart;


	public OrderItem(OrderRequestDto orderMenuRequestDto, Store store, Menu menu, Cart cart) {
		this.price = orderMenuRequestDto.getOrderItemPrice();
		this.quantity = orderMenuRequestDto.getOrderItemQuantity();
		this.store = store;
		this.menu = menu;
		this.cart = cart;
	}
}
