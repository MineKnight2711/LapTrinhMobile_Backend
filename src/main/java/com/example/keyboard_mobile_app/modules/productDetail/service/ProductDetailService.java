package com.example.keyboard_mobile_app.modules.productDetail.service;

import com.example.keyboard_mobile_app.entity.ProductDetail;
import com.example.keyboard_mobile_app.modules.ResponseBase;
import com.example.keyboard_mobile_app.shared.UploadImageService;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
}
