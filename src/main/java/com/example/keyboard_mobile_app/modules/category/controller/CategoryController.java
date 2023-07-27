package com.example.keyboard_mobile_app.modules.category.controller;

import com.example.keyboard_mobile_app.modules.ResponseBase;
import com.example.keyboard_mobile_app.modules.category.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    //Get Method
    @GetMapping()
    public ResponseBase getList() throws ExecutionException, InterruptedException {
        return categoryService.getList();
    }

    //Post Method
    @PostMapping("/create")
    public ResponseBase createBrand(
            @RequestParam String categoryName
    ){
        return categoryService.createCategory(categoryName);
    }
    //Put Method
    @PutMapping("/update/{id}")
    public ResponseBase updateCategory(
            @PathVariable("id") String id,
            @RequestParam("categoryName") String categoryName
    ) throws ExecutionException, InterruptedException {
        return categoryService.updateCategory(id, categoryName);
    }
}
