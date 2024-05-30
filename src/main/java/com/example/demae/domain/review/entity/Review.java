package com.example.demae.domain.review.entity;

import com.example.demae.domain.cart.entity.Cart;
import com.example.demae.domain.review.dto.ReviewRequestDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @Column
    private int reviewPoint;

    @Column
    private String reviewContent;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    @JsonIgnore
    private Cart cart;

    public Review(ReviewRequestDto reviewRequestDto, Cart cart) {
        this.reviewPoint = reviewRequestDto.getPoint();
        this.reviewContent = reviewRequestDto.getContent();
        this.cart = cart;
    }

    public void update(int point, String content) {
        this.reviewPoint = point;
        this.reviewContent = content;
    }
}
