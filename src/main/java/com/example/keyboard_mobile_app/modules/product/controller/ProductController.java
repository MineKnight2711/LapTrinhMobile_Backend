package com.example.keyboard_mobile_app.modules.product.controller;


import com.example.keyboard_mobile_app.modules.ResponseBase;
import com.example.keyboard_mobile_app.modules.product.dto.ProductDto;
import com.example.keyboard_mobile_app.modules.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    ProductService productService;
    @PostMapping("/create")
    public ResponseBase createProduct(
            @ModelAttribute ProductDto dto,
            @RequestParam(name = "displayFile", required = false) MultipartFile[] displayFile,
            @RequestParam(name = "imageFile", required = false) MultipartFile[] imageFile
    ) throws IOException {
        MultipartFile[] thumbImageToStore = displayFile;
        MultipartFile[] detailImagesToStore = imageFile;
        return productService.createProduct(thumbImageToStore, detailImagesToStore, dto);
    }
}
