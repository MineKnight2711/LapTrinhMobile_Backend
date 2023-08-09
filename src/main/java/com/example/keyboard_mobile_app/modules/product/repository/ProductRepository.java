package com.example.keyboard_mobile_app.modules.product.repository;

import com.example.keyboard_mobile_app.entity.Product;
import com.example.keyboard_mobile_app.modules.ResponseBase;
import com.example.keyboard_mobile_app.modules.product.dto.ProductDto;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Repository
public class ProductRepository {
    @Autowired
    private Firestore firestore;

    public Product createProduct(ProductDto dto) {
        CollectionReference collection = firestore.collection("product");
        Product product = new Product();
        product.setProductName(dto.getProductName());
        product.setUnit(dto.getUnit());
        product.setDescription(dto.getDescription());
        product.setCategory(dto.getCategoryId());
        product.setBrand(dto.getBrandId());
        product.setDisplayUrl(dto.getDisplayUrl());
        DocumentReference document = collection.document();
        product.setProductId(document.getId());
        document.set(product);
        return product;
    }

    public List<Product> getList() throws ExecutionException, InterruptedException {
        Firestore firestore = FirestoreClient.getFirestore();
        CollectionReference colRef = firestore.collection("product");
        ApiFuture<QuerySnapshot> future = colRef.get();
        QuerySnapshot snapshot = future.get();
        List<Product> lstProduct = new ArrayList<>();
        for (DocumentSnapshot document : snapshot.getDocuments()) {
            if (document.exists()) {
                Product product = document.toObject(Product.class);
                product.setProductId(document.getId());
                lstProduct.add(product);
            }
        }
        return lstProduct;
    }

    public Product getById(String productId) throws ExecutionException, InterruptedException {
        Firestore firestore = FirestoreClient.getFirestore();
        DocumentReference docRef = firestore.collection("product").document(productId);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();
        Product result = new Product();
        if (document.exists()) {
            result = document.toObject(Product.class);
            result.setProductId(document.getId());
            return result;
        }
        else return null;
    }

    public List<Product> getByCategoryId(String categoryId) throws ExecutionException, InterruptedException {
        Firestore firestore = FirestoreClient.getFirestore();
        CollectionReference colRef = firestore.collection("product");
        ApiFuture<QuerySnapshot> future = colRef.get();
        QuerySnapshot snapshot = future.get();
        List<Product> lstProduct = new ArrayList<>();
        for (DocumentSnapshot document : snapshot.getDocuments()) {
            if (document.exists()) {
                Product product = document.toObject(Product.class);
                product.setProductId(document.getId());
                if(product.getCategory().equals(categoryId))
                    lstProduct.add(product);
            }
        }
        return lstProduct;
    }

    public List<Product> getByBrandId(String brandId) throws ExecutionException, InterruptedException {
        Firestore firestore = FirestoreClient.getFirestore();
        CollectionReference colRef = firestore.collection("product");
        ApiFuture<QuerySnapshot> future = colRef.get();
        QuerySnapshot snapshot = future.get();
        List<Product> lstProduct = new ArrayList<>();
        for (DocumentSnapshot document : snapshot.getDocuments()) {
            if (document.exists()) {
                Product product = document.toObject(Product.class);
                product.setProductId(document.getId());
                if(product.getBrand().equals(brandId))
                    lstProduct.add(product);
            }
        }
        return lstProduct;
    }
}
