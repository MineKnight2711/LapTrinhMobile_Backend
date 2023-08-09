package com.example.keyboard_mobile_app.modules.review.service;

import com.example.keyboard_mobile_app.entity.Address;
import com.example.keyboard_mobile_app.entity.Review;
import com.example.keyboard_mobile_app.modules.ResponseBase;
import com.example.keyboard_mobile_app.modules.review.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;
    public ResponseBase getListReview(String productId) throws ExecutionException, InterruptedException {
        List<Review> reviewList = reviewRepository.getList(productId);
        return new ResponseBase(
                "Get list successfully!",
                reviewList
        );
    }
    public ResponseBase addReview(String accountId, Review review){
        Review result = reviewRepository.addReview(accountId,review);
        return new ResponseBase(
                "Create review successfully!",
                result
        );
    }
    public ResponseBase deleteReviewById(String reviewId) throws ExecutionException, InterruptedException {
        return new ResponseBase(
                reviewRepository.deleteReviewById(reviewId),
                null
        );
    }
}
