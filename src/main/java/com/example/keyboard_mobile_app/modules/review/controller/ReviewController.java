package com.example.keyboard_mobile_app.modules.review.controller;

import com.example.keyboard_mobile_app.entity.Address;
import com.example.keyboard_mobile_app.entity.Review;
import com.example.keyboard_mobile_app.modules.ResponseBase;
import com.example.keyboard_mobile_app.modules.review.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/review")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;
    @GetMapping("/{productId}")
    public ResponseBase getAllReview(@PathVariable("productId") String productId) throws ExecutionException, InterruptedException {
        return reviewService.getListReview(productId);
    }
    // Post Method
    @PostMapping("/create/{accountId}")
    public ResponseBase createReview(@PathVariable("accountId") String accountId, @ModelAttribute Review review)
    {
        return reviewService.addReview(accountId,review);
    }
    // Delete Method
    @DeleteMapping("/delete/{reviewId}")
    public ResponseBase deleteReviewById(
            @PathVariable("reviewId") String reviewId
    ) throws ExecutionException, InterruptedException {
        return reviewService.deleteReviewById(reviewId);
    }
}
