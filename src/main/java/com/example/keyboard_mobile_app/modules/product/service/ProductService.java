package com.example.keyboard_mobile_app.modules.product.service;

import com.example.keyboard_mobile_app.entity.Account;
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
    public ResponseBase createProduct(MultipartFile[] displayImage, ProductDto dto) throws IOException {
        CollectionReference collection = firestore.collection("product");

        // set product
        Product product = new Product();
        product.setProductName(dto.getProductName());
        product.setUnit(dto.getUnit());
        product.setDescription(dto.getDescription());
        if (displayImage != null) {
            List<String> displayUrl = uploadImageService.uploadFiles(displayImage);
            if (!displayUrl.isEmpty()) {
                product.setDisplayUrl(displayUrl.get(0));
            }
        }
        product.setCategory(dto.getCategoryId());
        product.setBrand(dto.getBrandId());
        DocumentReference document = collection.document();
        product.setProductId(document.getId());
        document.set(product);
        return new ResponseBase(
                "Create product succesfully!",
                product
        );
    }

    public ResponseBase getList() throws ExecutionException, InterruptedException {
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
        if (!lstProduct.isEmpty()) {
            return new ResponseBase(
                    "Get List Product",
                    lstProduct
            );
        } else {
            return new ResponseBase(
                    "No product found!",
                    null
            );
        }
    }
    public ResponseBase getById(String id) throws InterruptedException, ExecutionException {
        Firestore firestore = FirestoreClient.getFirestore();

        // Specify the path to the account document in Firestore
        DocumentReference docRef = firestore.collection("product").document(id);

        // Fetch the account document
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();
        Product result = new Product();
        if (document.exists()) {
            // Convert the document data to an AccountResponse object
            result = document.toObject(Product.class);
            result.setProductId(document.getId());
            return new ResponseBase(
                    "Product found!",
                    result
            );
        } else {
            // Account with the specified ID not found
            return new ResponseBase(
                    "Product not found!",
                    null
            );
        }
    }
    public ResponseBase getByCategoryId(String categoryId) throws ExecutionException, InterruptedException {
        Firestore firestore = FirestoreClient.getFirestore();
        CollectionReference colRef = firestore.collection("product");
        ApiFuture<QuerySnapshot> future = colRef.get();
        QuerySnapshot snapshot = future.get();
        List<Product> lstProduct = new ArrayList<>();
        for (DocumentSnapshot document : snapshot.getDocuments()) {
            if (document.exists()) {
                Product product = document.toObject(Product.class);
                product.setProductId(document.getId());
                System.out.println(product.getCategory().toString());
                System.out.println(categoryId);
                if(product.getCategory().equals(categoryId))
                    lstProduct.add(product);
            }
        }
        if (!lstProduct.isEmpty()) {
            return new ResponseBase(
                    "Get List Product By Category",
                    lstProduct
            );
        } else {
            return new ResponseBase(
                    "No product found!",
                    null
            );
        }
    }
    public ResponseBase getByBrand(String brandId) throws ExecutionException, InterruptedException {
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
        if (!lstProduct.isEmpty()) {
            return new ResponseBase(
                    "Get List Product By Brand",
                    lstProduct
            );
        } else {
            return new ResponseBase(
                    "No product found!",
                    null
            );
        }
    }
}
