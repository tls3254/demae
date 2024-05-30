package com.example.demae.domain.review.controller;

import com.example.demae.domain.review.dto.ReviewRequestDto;
import com.example.demae.domain.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/carts")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/{cartId}/reviews")
    public ResponseEntity<String> createReview(@PathVariable Long cartId,
                                              @RequestBody ReviewRequestDto reviewRequestDto,
                                              @AuthenticationPrincipal UserDetails userDetails){
        reviewService.createReview(cartId,reviewRequestDto,userDetails.getUsername());
        return ResponseEntity.ok("ok");
    }

    @PatchMapping("/{cartId}/reviews/{reviewId}")
    public ResponseEntity<String> patchReview(@PathVariable Long cartId,
                              @PathVariable Long reviewId,
                              @RequestBody ReviewRequestDto reviewRequestDto,
                              @AuthenticationPrincipal UserDetails userDetails){
        reviewService.patchReview(cartId, reviewId, reviewRequestDto, userDetails.getUsername());
        return ResponseEntity.ok("ok");
    }

    @DeleteMapping("/{cartId}/reviews/{reviewId}")
    @ResponseBody
    public ResponseEntity<String> deleteReview(@PathVariable Long cartId,
                             @PathVariable Long reviewId,
                             @AuthenticationPrincipal UserDetails userDetails){
        reviewService.deleteReview(cartId,reviewId,userDetails.getUsername());
        return ResponseEntity.ok("ok");
    }
}
