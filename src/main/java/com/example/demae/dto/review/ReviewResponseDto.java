package com.example.demae.dto.review;

import com.example.demae.entity.Review;
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
        this.id =checkReview.getId();
        this.point = checkReview.getPoint();
        this.content = checkReview.getContent();
        this.orderId =checkReview.getOrder().getId();
    }
}
