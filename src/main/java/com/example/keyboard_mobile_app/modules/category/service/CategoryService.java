package com.example.keyboard_mobile_app.modules.category.service;

import com.example.keyboard_mobile_app.entity.Brand;
import com.example.keyboard_mobile_app.entity.Category;
import com.example.keyboard_mobile_app.modules.ResponseBase;
import com.example.keyboard_mobile_app.modules.category.repository.CategoryRepository;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class CategoryService {
    @Autowired
    private Firestore firestore;
    @Autowired
    private CategoryRepository categoryRepository;

    public ResponseBase createCategory(String categoryName) {
        Category result = categoryRepository.createCategory(categoryName);
        return new ResponseBase(
                "Create Category Successfully!",
                result
        );
    }
    public ResponseBase updateCategory(String categoryId, String categoryNameUpdate) throws ExecutionException, InterruptedException {
        Category result = categoryRepository.updateCategory(categoryId, categoryNameUpdate);
        return new ResponseBase(
                "Update Brand successfully!",
                result
        );
    }

    public ResponseBase getList() throws ExecutionException, InterruptedException {
        List<Category> result = categoryRepository.getList();
        if (!result.isEmpty()) {
            return new ResponseBase(
                    "Get List Category",
                    result
            );
        } else {
            return new ResponseBase(
                    "No categories found!",
                    null
            );
        }
    }
}
