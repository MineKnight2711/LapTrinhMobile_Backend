package com.example.keyboard_mobile_app.modules.brand.service;

import com.example.keyboard_mobile_app.entity.Brand;
import com.example.keyboard_mobile_app.modules.ResponseBase;
import com.example.keyboard_mobile_app.modules.account.dto.AccountResponseDto;
import com.example.keyboard_mobile_app.modules.brand.controller.BrandController;
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

    public ResponseBase createBrand(String brandName) {
        CollectionReference collection = firestore.collection("brand");
        Brand brand = new Brand();
        brand.setBrandName(brandName);
        DocumentReference document = collection.document();
        document.set(brand);
        return new ResponseBase(
                "Create brand succesfully!",
                brand
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
