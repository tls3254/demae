package com.example.demae.domain.review.repository;

import com.example.demae.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review,Long> {

    List<Review> findByCart_CartId(Long cartId);
}
