package com.example.keyboard_mobile_app.modules.productDetail.service;

import com.example.keyboard_mobile_app.entity.ProductDetail;
import com.example.keyboard_mobile_app.modules.ResponseBase;
import com.example.keyboard_mobile_app.shared.UploadImageService;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class ProductDetailService {
    @Autowired
    private Firestore firestore;
    @Autowired
    private UploadImageService uploadImageService;

    public ResponseBase createProductDetail(MultipartFile[] displayImage, ProductDetail productDetail) throws IOException {
        CollectionReference collection = firestore.collection("productDetail");
        DocumentReference document = collection.document();
        ProductDetail result = new ProductDetail();
        result.setProductDetailId(document.getId());
        result.setPrice(productDetail.getPrice());
        result.setColor(productDetail.getColor());
        result.setQuantity(productDetail.getQuantity());
        result.setProductId(productDetail.getProductId());
        List<String> lstImage = new ArrayList<>();
        String descriptionImageLists;
        if (displayImage != null) {
            List<String> imageUrls = uploadImageService.uploadFiles(displayImage);
            lstImage.addAll(imageUrls);
            descriptionImageLists = new Gson().toJson(lstImage);
            result.setImageUrl(descriptionImageLists);
        }
        document.set(result);
        return new ResponseBase(
                "Create product detail successfully!",
                result
        );
    }

    public ResponseBase getListProduct() throws ExecutionException, InterruptedException {
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
        if (!lstProductDetail.isEmpty()) {
            return new ResponseBase(
                    "Get List Product Detail",
                    lstProductDetail
            );
        } else {
            return new ResponseBase(
                    "No Product found!",
                    null
            );
        }
    }
    public ResponseBase getListByProductId(String productId) throws ExecutionException, InterruptedException {
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
        if (!lstProductDetail.isEmpty()) {
            return new ResponseBase(
                    "Get List Product Detail By ProductId",
                    lstProductDetail
            );
        } else {
            return new ResponseBase(
                    "No Product found!",
                    null
            );
        }
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
