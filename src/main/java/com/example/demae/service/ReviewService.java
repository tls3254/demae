package com.example.demae.service;

import com.example.demae.dto.menu.MenuResponseDto;
import com.example.demae.dto.review.ReviewRequestDto;
import com.example.demae.dto.review.ReviewResponseDto;
import com.example.demae.entity.Menu;
import com.example.demae.entity.Order;
import com.example.demae.entity.Review;
import com.example.demae.repository.OrderRepository;
import com.example.demae.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<Review> getReview(Long orderId) {
        List<Review> reviewCheck = reviewRepository.findByOrderId(orderId);
        List<Review> newList = new ArrayList<>(reviewCheck);
        return newList;
    }
}
//    List<Menu> newList = new ArrayList<>(storeCheck);
//
//    List<MenuResponseDto> menuResponseDto = new ArrayList<>();;
//        for (Menu menu : storeCheck) {
//                List<String> pictureUrls = awsS3Service.getObjectUrlsForMenu(menu.getId());
//        menuResponseDto.add(new MenuResponseDto(menu, pictureUrls));
//        }
//        return menuResponseDto;
