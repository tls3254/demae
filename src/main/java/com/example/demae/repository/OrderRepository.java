package com.example.demae.repository;

import com.example.demae.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {
    Order findByUserIdAndId(Long userId, Long id);

    List<Order> findByUserId(Long userId);

    List<Order> findByStoreId(Long userId);

    Order findByReviewsId(Long reviewId);
}
