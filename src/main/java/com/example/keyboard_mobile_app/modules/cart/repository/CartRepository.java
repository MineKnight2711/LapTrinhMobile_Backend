package com.example.keyboard_mobile_app.modules.cart.repository;

import com.example.keyboard_mobile_app.entity.Cart;
import com.example.keyboard_mobile_app.modules.ResponseBase;
import com.example.keyboard_mobile_app.modules.cart.dto.ItemProductDetail;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Repository
public class CartRepository {
    @Autowired
    private Firestore firestore = FirestoreClient.getFirestore();

    private CollectionReference collection = firestore.collection("cart");

    public List<Cart> getByAccountId(String accountId) throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> future = collection.get();
        QuerySnapshot snapshot = future.get();
        List<Cart> lstCart = new ArrayList<>();
        for (DocumentSnapshot document : snapshot.getDocuments()) {
            if (document.exists()) {
                Cart cart = document.toObject(Cart.class);
                if(cart.getAccountId().equals(accountId))
                    lstCart.add(cart);
            }
        }
        return lstCart;
    }

    public void updateCart(String accountId, String productDetailId, int quantity) throws ExecutionException, InterruptedException {
        CollectionReference collection = firestore.collection("cart");
        ApiFuture<QuerySnapshot> future = collection.whereEqualTo("accountId", accountId)
                .whereEqualTo("productDetailId", productDetailId)
                .get();
        QuerySnapshot snapshot = future.get();
        if (!snapshot.isEmpty()) {
            DocumentSnapshot documentSnapshot = snapshot.getDocuments().get(0);
            Cart cart = documentSnapshot.toObject(Cart.class);
            Map<String, Object> cartMap = new HashMap<>();
            cart.quantity += quantity;
            cartMap.put("accountId", cart.getAccountId());
            cartMap.put("productDetailId", cart.getProductDetailId());
            cartMap.put("quantity", cart.quantity);
            documentSnapshot.getReference().set(cartMap).get();
        }
    }

    public void addToCart(String accountId, String productDetailId, int quantity) {
        DocumentReference document = collection.document();
        Cart cart = new Cart();
        cart.setAccountId(accountId);
        cart.setProductDetailId(productDetailId);
        cart.setQuantity(quantity);
        document.set(cart);
    }

    public void deleteManyItems(String accountId, List<ItemProductDetail> lstProductDetail) throws ExecutionException, InterruptedException {
        Firestore firestore = FirestoreClient.getFirestore();
        CollectionReference colRef = firestore.collection("cart");
        for (ItemProductDetail item: lstProductDetail) {
            Query query = colRef.whereEqualTo("accountId", accountId)
                    .whereEqualTo("productDetailId", item.productDetailId);
            ApiFuture<QuerySnapshot> future = query.get();
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();
            for (QueryDocumentSnapshot document : documents) {
                document.getReference().delete();
            }
        }
    }
}
