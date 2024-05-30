package com.example.demae.domain.review.dto;

import com.example.demae.domain.review.entity.Review;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewResponseDto {
    private Long id;
    private int point;
    private String content;
    private Long orderId;

    public ReviewResponseDto(Review checkReview) {
        this.id =checkReview.getReviewId();
        this.point = checkReview.getReviewPoint();
        this.content = checkReview.getReviewContent();
        this.orderId =checkReview.getCart().getCartId();
    }
}
