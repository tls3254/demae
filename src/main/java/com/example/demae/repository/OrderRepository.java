package com.example.demae.repository;

import com.example.demae.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
    Order findByUserId(Long id);

    Order findByReviewsId(Long reviewId);
}
