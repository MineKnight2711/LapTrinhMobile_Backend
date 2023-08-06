package com.example.keyboard_mobile_app.modules.brand.service;

import com.example.keyboard_mobile_app.entity.Brand;
import com.example.keyboard_mobile_app.modules.ResponseBase;
import com.example.keyboard_mobile_app.modules.account.dto.AccountResponseDto;
import com.example.keyboard_mobile_app.modules.brand.controller.BrandController;
import com.example.keyboard_mobile_app.modules.brand.repository.BrandRepository;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutionException;

@Service
public class BrandService {
    @Autowired
    private Firestore firestore;
    @Autowired
    private BrandRepository brandRepository;

    public ResponseBase createBrand(String brandName) {
        Brand result = brandRepository.createBrand(brandName);
        return new ResponseBase(
                "Create brand succesfully!",
                result
        );
    }

    public ResponseBase updateBrand(String brandId, String brandNameUpdate) throws ExecutionException, InterruptedException {
        Brand result = brandRepository.updateBrand(brandId, brandNameUpdate);
        return new ResponseBase(
                "Update Brand successfully!",
                result
        );
    }

    public ResponseBase getList() throws ExecutionException, InterruptedException {
        List<Brand> result = brandRepository.getList();
        if (!result.isEmpty()) {
            return new ResponseBase(
                    "Get List Brand",
                    result
            );
        } else {
            return new ResponseBase(
                    "No brands found!",
                    null
            );
        }
    }
}
