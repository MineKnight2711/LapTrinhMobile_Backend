package com.example.keyboard_mobile_app.modules.productDetail.repository;
import com.example.keyboard_mobile_app.entity.ProductDetail;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Repository
public class ProductDetailRepository {
    @Autowired
    private Firestore firestore;

    public ProductDetail createProduct(ProductDetail productDetail) {
        CollectionReference collection = firestore.collection("productDetail");
        DocumentReference document = collection.document();
        ProductDetail result = new ProductDetail();
        result.setProductDetailId(document.getId());
        result.setPrice(productDetail.getPrice());
        result.setColor(productDetail.getColor());
        result.setQuantity(productDetail.getQuantity());
        result.setProductId(productDetail.getProductId());
        result.setImageUrl(productDetail.getImageUrl());
        document.set(result);
        return result;
    }

    public ProductDetail getById(String id) throws ExecutionException, InterruptedException {
        Firestore firestore = FirestoreClient.getFirestore();
        // Specify the path to the account document in Firestore
        DocumentReference docRef = firestore.collection("productDetail").document(id);
        // Fetch the account document
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();
        ProductDetail result = new ProductDetail();
        if (document.exists()) {
            result = document.toObject(ProductDetail.class);
            result.setProductDetailId(document.getId());
            return result;
        } else {
            // Account with the specified ID not found
            return null;
        }
    }
    public List<ProductDetail> getListProductDetail() throws ExecutionException, InterruptedException {
        Firestore firestore = FirestoreClient.getFirestore();
        CollectionReference colRef = firestore.collection("productDetail");
        ApiFuture<QuerySnapshot> future = colRef.get();
        QuerySnapshot snapshot = future.get();
        List<ProductDetail> lstProductDetail = new ArrayList<>();
        for (DocumentSnapshot document : snapshot.getDocuments()) {
            if (document.exists()) {
                ProductDetail productDetail = document.toObject(ProductDetail.class);
                lstProductDetail.add(productDetail);
            }
        }
        return lstProductDetail;
    }
    public String deleteDetail(String id) throws ExecutionException, InterruptedException {
        Firestore firestore = FirestoreClient.getFirestore();
        DocumentReference docRef = firestore.collection("productDetail").document(id);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();
        if (document.exists()) {
            docRef.delete();
            return "Product details deleted!";
        } else {
            return "Product detail not found!";
        }
    }
    public List<ProductDetail> getByProductId (String productId) throws ExecutionException, InterruptedException {
        Firestore firestore = FirestoreClient.getFirestore();
        CollectionReference colRef = firestore.collection("productDetail");
        ApiFuture<QuerySnapshot> future = colRef.get();
        QuerySnapshot snapshot = future.get();
        List<ProductDetail> lstProductDetail = new ArrayList<>();
        for (DocumentSnapshot document : snapshot.getDocuments()) {
            if (document.exists()) {
                ProductDetail productDetail = document.toObject(ProductDetail.class);
                if(productDetail.getProductId().equals(productId))
                    lstProductDetail.add(productDetail);
            }
        }
        return lstProductDetail;
    }
    public boolean checkSl(String productDetailId, int quantity) throws ExecutionException, InterruptedException {
        Firestore firestore = FirestoreClient.getFirestore();
        CollectionReference colRef = firestore.collection("productDetail");
        ApiFuture<QuerySnapshot> future = colRef.get();
        QuerySnapshot snapshot = future.get();
        for (DocumentSnapshot document : snapshot.getDocuments()) {
            if (document.exists()) {
                ProductDetail productDetail = document.toObject(ProductDetail.class);
                if(productDetail.getProductId().equals(productDetailId) && productDetail.getQuantity() != quantity)
                    return false;
            }
        }
        return true;
    }
}
