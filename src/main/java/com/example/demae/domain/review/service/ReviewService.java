package com.example.demae.domain.review.service;

import com.example.demae.domain.cart.entity.Cart;
import com.example.demae.domain.cart.repository.CartRepository;
import com.example.demae.domain.review.dto.ReviewRequestDto;
import com.example.demae.domain.review.dto.ReviewResponseDto;
import com.example.demae.domain.review.entity.Review;
import com.example.demae.domain.review.repository.ReviewRepository;
import com.example.demae.domain.user.entity.User;
import com.example.demae.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final CartRepository cartRepository;
    private final UserService userService;

    public void createReview(Long cartId, ReviewRequestDto reviewRequestDto, String userEmail) {
        User user = userService.findUser(userEmail);
        Cart cart = cartRepository.findByUser_UserIdAndCartId(user.getUserId(), cartId);
        List<Review> review1 = reviewRepository.findByCart_CartId(cartId);
        for(Review review2:review1){
            if(review2.getCart().getCartId().equals(cartId)){
                throw new IllegalArgumentException("리뷰가 존재합니다.");
            }
        }
        if (cart == null) {
            throw new IllegalArgumentException("주문이 존재하지 않습니다.");
        }
        Review review = new Review(reviewRequestDto, cart);
        reviewRepository.save(review);
    }

    public void patchReview(Long orderId, Long reviewId,ReviewRequestDto reviewRequestDto,String userEmail) {
        Cart findOrder = cartRepository.findById(orderId).orElseThrow(()-> new IllegalArgumentException("본인의 주문이 아닙니다."));
        Review review = reviewRepository.findById(reviewId).orElseThrow(()->new IllegalArgumentException("리뷰가 없습니다."));
        if(!findOrder.getUser().getUserEmail().equals(userEmail)){
            throw new IllegalArgumentException("본인의 가게가 아닙니다.");
        }
        review.update(reviewRequestDto.getPoint(),reviewRequestDto.getContent());
    }

    public void deleteReview(Long orderId, Long reviewId, String userEmail) {
        Cart FindOrder = cartRepository.findById(orderId).orElseThrow(()->new IllegalArgumentException("본인의 주문이 아닙니다."));
        Review findReview = reviewRepository.findById(reviewId).orElseThrow(()->new IllegalArgumentException("리뷰가 없습니다."));
        if(!FindOrder.getUser().getUserEmail().equals(userEmail)){
            throw new IllegalArgumentException("본인의 가게가 아닙니다.");
        }
        reviewRepository.delete(findReview);
        System.out.println("sds");

    }

    @Transactional(readOnly = true)
    public List<ReviewResponseDto> getAllReview(Long cartId) {
        List<Review> reviewCheck = reviewRepository.findByCart_CartId(cartId);
        return reviewCheck.stream().map(ReviewResponseDto::new).toList();
    }

    @Transactional(readOnly = true)
    public ReviewResponseDto singleReview(Long cartId, Long reviewId) {
        Review checkReview = reviewRepository.findById(reviewId).orElseThrow(()->new IllegalArgumentException("리뷰가 없습니다."));
        return new ReviewResponseDto(checkReview);
    }
}
