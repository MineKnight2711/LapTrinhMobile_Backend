package com.example.keyboard_mobile_app.modules.product.controller;


import com.example.keyboard_mobile_app.modules.ResponseBase;
import com.example.keyboard_mobile_app.modules.product.dto.ProductDto;
import com.example.keyboard_mobile_app.modules.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    ProductService productService;

    //Get Method
    @GetMapping
    public ResponseBase getList() throws ExecutionException, InterruptedException {
        return productService.getList();
    }
    @GetMapping("/getById/{id}")
    public ResponseBase getById(@PathVariable("id") String id) throws ExecutionException, InterruptedException {
        return productService.getById(id);
    }
    @GetMapping("/category/{categoryId}")
    public ResponseBase getByCategory(
            @PathVariable("categoryId") String categoryId
    ) throws ExecutionException, InterruptedException {
        return productService.getByCategoryId(categoryId);
    }
    @GetMapping("/brand/{brandId}")
    public ResponseBase getByBrand(
            @PathVariable("brandId") String brandId
    ) throws ExecutionException, InterruptedException {
        return productService.getByBrand(brandId);
    }

    //Post Method
    @PostMapping("/create")
    public ResponseBase createProduct(
            @ModelAttribute ProductDto dto,
            @RequestParam(name = "displayFile", required = false) MultipartFile[] displayFile
    ) throws IOException {
        MultipartFile[] thumbImageToStore = displayFile;
        return productService.createProduct(thumbImageToStore, dto);
    }
}
