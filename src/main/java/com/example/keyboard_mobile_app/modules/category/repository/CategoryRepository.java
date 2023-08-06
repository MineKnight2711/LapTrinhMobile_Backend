package com.example.keyboard_mobile_app.modules.category.repository;

import com.example.keyboard_mobile_app.entity.Category;
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
public class CategoryRepository {
    @Autowired
    private Firestore firestore;

    public Category createCategory(String categoryName) {
        CollectionReference collection = firestore.collection("category");
        Category category = new Category();
        category.setCategoryName(categoryName);
        DocumentReference document = collection.document();
        category.setCategoryId(document.getId());
        document.set(category);
        return category;
    }

    public Category updateCategory(String categoryId, String categoryNameUpdate) throws ExecutionException, InterruptedException {
        DocumentReference document = firestore.collection("category").document(categoryId);
        Map<String, Object> categoryMap = new HashMap<>();
        categoryMap.put("categoryName", categoryNameUpdate);
        document.update(categoryMap).get();
        return (Category) categoryMap;
    }

    public List<Category> getList() throws ExecutionException, InterruptedException {
        Firestore firestore = FirestoreClient.getFirestore();
        CollectionReference colRef = firestore.collection("category");
        ApiFuture<QuerySnapshot> future = colRef.get();
        QuerySnapshot snapshot = future.get();
        List<Category> lstCategory = new ArrayList<>();
        for (DocumentSnapshot document : snapshot.getDocuments()) {
            if (document.exists()) {
                Category category = document.toObject(Category.class);
                category.setCategoryId(document.getId());
                lstCategory.add(category);
            }
        }
        return lstCategory;
    }
}
