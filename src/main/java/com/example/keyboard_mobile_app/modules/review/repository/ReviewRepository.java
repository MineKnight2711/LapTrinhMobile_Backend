package com.example.keyboard_mobile_app.modules.review.repository;

import com.example.keyboard_mobile_app.entity.Address;
import com.example.keyboard_mobile_app.entity.Product;
import com.example.keyboard_mobile_app.entity.Review;
import com.example.keyboard_mobile_app.modules.cart.dto.ItemProductDetail;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.FirebaseException;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Repository
public class ReviewRepository {
    @Autowired
    private Firestore firestore;

    private CollectionReference reference;
    public List<Review> getList(String productId) throws ExecutionException, InterruptedException {
        firestore = FirestoreClient.getFirestore();
        Query reference = firestore.collection("review").whereEqualTo("productId", productId); // Changed from whereIn to whereEqualTo
        ApiFuture<QuerySnapshot> future = reference.get();
        QuerySnapshot snapshot = future.get();
        List<Review> lstReview = new ArrayList<>(); // Changed variable name to lstReview
        for (DocumentSnapshot document : snapshot.getDocuments()) {
            if (document.exists()) {
                Review review = document.toObject(Review.class);
                review.setReviewId(document.getId()); // Changed from setProductId to setReviewId
                lstReview.add(review); // Changed variable name from lstProduct to lstReview
            }
        }
        return lstReview; // Changed variable name from lstProduct to lstReview
    }

    public Review addReview(String accountId, Review review) {
        reference = firestore.collection("review");
        DocumentReference document = reference.document();
        Review newReview = new Review();
        newReview.setReviewId(document.getId());
        newReview.setAccountId(accountId);
        newReview.setProductId(review.getProductId());
        newReview.setDateReview(review.getDateReview());
        newReview.setStar(review.getStar());
        newReview.setComment(review.getComment());
        document.set(newReview);
        return newReview;
    }

    public String deleteReviewById(String reviewId) throws ExecutionException, InterruptedException {
        Firestore firestore = FirestoreClient.getFirestore();
        DocumentReference docRef = firestore.collection("review").document(reviewId);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();
        if (document.exists()) {
            docRef.delete();
            return "Review deleted!";
        } else {
            return "Review not found!";
        }
    }
}
