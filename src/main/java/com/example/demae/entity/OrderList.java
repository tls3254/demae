package com.example.demae.entity;

import com.example.demae.dto.order.OrderMenuRequestDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "orderList")
public class OrderList {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "order_id")
	private Order order;

	@Column(nullable = false)
	private Long menuId;

	@Column(nullable = false)
	private String menuName;

	@Column(nullable = false)
	private int price;

	@Column(nullable = false)
	private int quantity;

	@Column(nullable = false)
	private Long storeId;


	public OrderList(Order order, OrderMenuRequestDto orderMenuRequestDto) {
		this.order = order;
		this.menuId = orderMenuRequestDto.getId();
		this.menuName = orderMenuRequestDto.getName();
		this.price = orderMenuRequestDto.getPrice();
		this.quantity = orderMenuRequestDto.getQuantity();
		this.storeId = orderMenuRequestDto.getStoreId();
	}
}
