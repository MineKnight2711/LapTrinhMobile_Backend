package com.example.keyboard_mobile_app.modules.product.service;

import com.example.keyboard_mobile_app.entity.Brand;
import com.example.keyboard_mobile_app.entity.Product;
import com.example.keyboard_mobile_app.modules.ResponseBase;
import com.example.keyboard_mobile_app.modules.brand.service.BrandService;
import com.example.keyboard_mobile_app.modules.category.service.CategoryService;
import com.example.keyboard_mobile_app.modules.product.dto.ProductDto;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class ProductService {
    @Autowired
    private Firestore firestore;
    @Autowired
    private UploadImageService uploadImageService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private BrandService brandService;
    public ResponseBase createProduct(MultipartFile[] displayImage, MultipartFile[] images, ProductDto dto) throws IOException {
        CollectionReference collection = firestore.collection("product");

        // set product
        Product product = new Product();
        product.setProductName(dto.name);
        product.setPrice(dto.price);
        product.setQuantity(dto.quantity);
        product.setUnit(dto.unit);
        product.setDescription(dto.description);

        // get url display image
        //String displayUrl= uploadImageService.uploadImage(dto.displayFile,"productImage/", dto.name);
        //product.setDisplayUrl(displayUrl);

        // get url images
        List<String> productImageList = new ArrayList<>();
        //List<String> imageUrls = uploadImageService.uploadImage(dto.imageFile,"productImage/", dto.name);

        if (displayImage != null) {
            List<String> displayUrl = uploadImageService.uploadFiles(images);
            if (!displayUrl.isEmpty()) {
                product.setDisplayUrl(displayUrl.get(0));
            }
        }

        List<String> lstImage = new ArrayList<>();
        String descriptionImageLists = null;
        if (images != null) {
            List<String> imageUrls = uploadImageService.uploadFiles(images);
            lstImage.addAll(imageUrls);
            descriptionImageLists = new Gson().toJson(productImageList);
            product.setImageUrl(descriptionImageLists);
        }
        product.setBrand(dto.brandId);
        DocumentReference document = collection.document();
        product.setProductId(document.getId());
        document.set(product);
        return new ResponseBase(
                "Create brand succesfully!",
                product
        );
    }

    public ResponseBase updateBrand(String brandId, String brandNameUpdate) throws ExecutionException, InterruptedException {
        DocumentReference document = firestore.collection("brand").document(brandId);
        Map<String, Object> brandMap = new HashMap<>();
        brandMap.put("brandName", brandNameUpdate);
        document.update(brandMap).get();
        return new ResponseBase(
                "Update Brand successfully!",
                brandMap
        );
    }

    public ResponseBase getList() throws ExecutionException, InterruptedException {
        Firestore firestore = FirestoreClient.getFirestore();
        CollectionReference colRef = firestore.collection("brand");
        ApiFuture<QuerySnapshot> future = colRef.get();
        QuerySnapshot snapshot = future.get();
        List<Brand> lstBrand = new ArrayList<>();
        for (DocumentSnapshot document : snapshot.getDocuments()) {
            if (document.exists()) {
                Brand brand = document.toObject(Brand.class);
                brand.setBrandID(document.getId());
                lstBrand.add(brand);
            }
        }
        if (!lstBrand.isEmpty()) {
            return new ResponseBase(
                    "Get List Brand",
                    lstBrand
            );
        } else {
            return new ResponseBase(
                    "No brands found!",
                    null
            );
        }
    }
}
