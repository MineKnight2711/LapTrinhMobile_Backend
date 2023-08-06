package com.example.keyboard_mobile_app.modules.product.service;

import com.example.keyboard_mobile_app.entity.Account;
import com.example.keyboard_mobile_app.entity.Brand;
import com.example.keyboard_mobile_app.entity.Product;
import com.example.keyboard_mobile_app.modules.ResponseBase;
import com.example.keyboard_mobile_app.modules.brand.service.BrandService;
import com.example.keyboard_mobile_app.modules.category.service.CategoryService;
import com.example.keyboard_mobile_app.modules.product.dto.ProductDto;
import com.example.keyboard_mobile_app.modules.product.repository.ProductRepository;
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
    private ProductRepository productRepository;
    public ResponseBase createProduct(MultipartFile[] displayImage, ProductDto dto) throws IOException {
        if (displayImage != null) {
            List<String> displayUrl = uploadImageService.uploadFiles(displayImage);
            if (!displayUrl.isEmpty()) {
                dto.setDisplayUrl(displayUrl.get(0));
            }
        }
        Product result = productRepository.createProduct(dto);
        return new ResponseBase(
                "Create product succesfully!",
                result
        );
    }

    public ResponseBase getList() throws ExecutionException, InterruptedException {
        List<Product> result = productRepository.getList();
        if (!result.isEmpty()) {
            return new ResponseBase(
                    "Get List Product",
                    result
            );
        } else {
            return new ResponseBase(
                    "No product found!",
                    null
            );
        }
    }
    public ResponseBase getById(String id) throws InterruptedException, ExecutionException {
        Product result = productRepository.getById(id);
        if (result != null) {
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
        List<Product> result = productRepository.getByCategoryId(categoryId);
        if (!result.isEmpty()) {
            return new ResponseBase(
                    "Get List Product By Category",
                    result
            );
        } else {
            return new ResponseBase(
                    "No product found!",
                    null
            );
        }
    }
    public ResponseBase getByBrand(String brandId) throws ExecutionException, InterruptedException {
        List<Product> result = productRepository.getByBrandId(brandId);
        if (!result.isEmpty()) {
            return new ResponseBase(
                    "Get List Product By Brand",
                    result
            );
        } else {
            return new ResponseBase(
                    "No product found!",
                    null
            );
        }
    }
}
