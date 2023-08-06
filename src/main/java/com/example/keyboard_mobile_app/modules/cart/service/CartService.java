package com.example.keyboard_mobile_app.modules.cart.service;
import com.example.keyboard_mobile_app.entity.Cart;
import com.example.keyboard_mobile_app.modules.ResponseBase;
import com.example.keyboard_mobile_app.modules.cart.dto.ItemProductDetail;
import com.example.keyboard_mobile_app.modules.cart.repository.CartRepository;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class CartService {
    @Autowired
    private Firestore firestore = FirestoreClient.getFirestore();
    @Autowired
    private CartRepository cartRepository;
    private CollectionReference collection = firestore.collection("cart");
    public ResponseBase getByAccountId(String accountId) throws ExecutionException, InterruptedException {
        List<Cart> result = cartRepository.getByAccountId(accountId);
        if (!result.isEmpty()) {
            return new ResponseBase(
                    "Get List Cart",
                    result
            );
        } else {
            return new ResponseBase(
                    "No cart found!",
                    null
            );
        }
    }

    public ResponseBase addToCart(String accountId, String productDetailId, int quantity) throws ExecutionException, InterruptedException {
        CollectionReference collection = firestore.collection("cart");
        ApiFuture<QuerySnapshot> future = collection.whereEqualTo("accountId", accountId)
                .whereEqualTo("productDetailId", productDetailId)
                .get();
        QuerySnapshot snapshot = future.get();
        if (!snapshot.isEmpty()) {
            cartRepository.updateCart(accountId, productDetailId, quantity);
            return new ResponseBase(
                    "Update cart",
                    null
            );
        } else {
            cartRepository.addToCart(accountId, productDetailId, quantity);
            return new ResponseBase(
                    "Add to Cart Successfully!",
                    null
            );
        }
    }
    public ResponseBase updateCart(String accountId, String productDetailId, int updatedQuantity) throws ExecutionException, InterruptedException{
        CollectionReference collection = firestore.collection("cart");
        ApiFuture<QuerySnapshot> future = collection.whereEqualTo("accountId", accountId)
                .whereEqualTo("productDetailId", productDetailId)
                .get();
        QuerySnapshot snapshot = future.get();
        if(!snapshot.isEmpty()){
            DocumentSnapshot documentSnapshot = snapshot.getDocuments().get(0);
            String cartId = documentSnapshot.getId();
            // Update the cart quantity using the cart document ID
            DocumentReference cartReference = collection.document(cartId);
            Map<String, Object> updateData = new HashMap<>();
            updateData.put("quantity", updatedQuantity);
            cartReference.update(updateData);
            return new ResponseBase(
                    "Update cart Successfully!",
                    null
            );
        }
        return new ResponseBase(
                "Update cart fail!!",
                null
        );
    }
    public ResponseBase deleteManyItems(String accountId, List<ItemProductDetail> lstProductDetail) throws ExecutionException, InterruptedException {
        cartRepository.deleteManyItems(accountId, lstProductDetail);
        return new ResponseBase(
                "Delete items Successfully!",
                null
        );
    }
}
