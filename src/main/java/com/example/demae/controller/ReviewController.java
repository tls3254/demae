package com.example.demae.controller;

import com.example.demae.dto.review.ReviewRequestDto;
import com.example.demae.entity.Review;
import com.example.demae.security.UserDetailsImpl;
import com.example.demae.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/stores")
public class ReviewController {
    private final ReviewService reviewService;
    @GetMapping("/{orderId}/createreview")
    public String showCreateReviewPage(Model model,@PathVariable Long orderId){
        model.addAttribute("orderId",orderId);
        return "createReview";
    }
    @PostMapping("/{orderId}/review")
    public String createReview(@PathVariable Long orderId,
                               @RequestBody ReviewRequestDto reviewRequestDto,
                               @AuthenticationPrincipal UserDetailsImpl userDetails){
        Long id = userDetails.getUser().getId();
        reviewService.createReview(orderId,reviewRequestDto,id);
        return "showMenuPage";
    }

    @GetMapping("/{orderId}/review")
    public String getReview(@PathVariable Long orderId,
                            Model model){
        List<Review> review = reviewService.getReview(orderId);
        model.addAttribute("reviewList", review);
        return "showReview";
    }
//
//    @PathVariable
//    @DeleteMapping
}
