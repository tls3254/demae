package com.example.demae.repository;

import com.example.demae.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
    Order findByReviewsId(Long reviewId);

    Order findByUserIdAndId(Long id, Long orderId);
}
