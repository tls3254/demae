package com.example.demae.domain.cart.repository;

import com.example.demae.domain.cart.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findByCart_CartId(Long cartId);
}
