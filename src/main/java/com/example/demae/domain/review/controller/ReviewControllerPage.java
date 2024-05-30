package com.example.demae.domain.review.controller;

import com.example.demae.domain.review.dto.ReviewResponseDto;
import com.example.demae.domain.review.entity.Review;
import com.example.demae.domain.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/carts")
public class ReviewControllerPage {

    private final ReviewService reviewService;

    @GetMapping("/{cartId}/createReview")
    public String showCreateReviewPage(@PathVariable Long cartId,
                                       Model model){
        model.addAttribute("cartId",cartId);
        return "review/createReview";
    }

    @GetMapping("/{cartId}/multiReview")
    public String multiReview(@PathVariable Long cartId,
                              Model model){
        List<ReviewResponseDto> review = reviewService.getAllReview(cartId);
        model.addAttribute("reviewList", review);
        return "review/showReview";
    }

    @GetMapping("/{cartId}/singleReview/{reviewId}")
    public String singleReview(@PathVariable Long cartId,
                               @PathVariable Long reviewId,
                               Model model){
        ReviewResponseDto selectReview = reviewService.singleReview(cartId, reviewId);
        model.addAttribute("reviewOne",selectReview);
        return "review/showSingleReview";
    }
}
