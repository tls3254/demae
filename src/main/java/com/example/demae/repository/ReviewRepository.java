package com.example.demae.repository;

import com.example.demae.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review,Long> {
    List<Review> findByOrderId(Long orderId);
}
