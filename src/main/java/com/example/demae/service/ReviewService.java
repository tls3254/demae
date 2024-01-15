package com.example.demae.service;

import com.example.demae.dto.review.ReviewRequestDto;
import com.example.demae.entity.Order;
import com.example.demae.entity.Review;
import com.example.demae.repository.OrderRepository;
import com.example.demae.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final OrderRepository orderRepository;

    public void createReview(Long orderId, ReviewRequestDto reviewRequestDto, Long id) {
        Order order = orderRepository.findByUserId(id);

        if (order == null) {
            throw new IllegalArgumentException("주문이 존재하지 않습니다.");
        }

        if (order.getId() != orderId) {
            throw new IllegalArgumentException("주문 정보가 일치하지 않습니다.");
        }

        Review review = new Review(reviewRequestDto, order);
        reviewRepository.save(review);
    }
}
