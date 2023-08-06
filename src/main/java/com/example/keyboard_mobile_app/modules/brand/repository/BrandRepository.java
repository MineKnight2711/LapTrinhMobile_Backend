package com.example.keyboard_mobile_app.modules.brand.repository;

import com.example.keyboard_mobile_app.entity.Brand;
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
public class BrandRepository {
    @Autowired
    private Firestore firestore;

    public Brand createBrand(String brandName) {
        CollectionReference collection = firestore.collection("brand");
        Brand brand = new Brand();
        brand.setBrandName(brandName);
        DocumentReference document = collection.document();
        brand.setBrandID(document.getId());
        document.set(brand);
        return brand;
    }

    public Brand updateBrand(String brandId, String brandNameUpdate) throws ExecutionException, InterruptedException {
        DocumentReference document = firestore.collection("brand").document(brandId);
        Map<String, Object> brandMap = new HashMap<>();
        brandMap.put("brandName", brandNameUpdate);
        document.update(brandMap).get();
        return (Brand) brandMap;
    }

    public List<Brand> getList() throws ExecutionException, InterruptedException {
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
        return lstBrand;
    }
}
