package com.example.keyboard_mobile_app.modules.productDetail.controller;

import com.example.keyboard_mobile_app.entity.ProductDetail;
import com.example.keyboard_mobile_app.modules.ResponseBase;
import com.example.keyboard_mobile_app.modules.productDetail.service.ProductDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/productDetail")
public class ProductDetailController {
    @Autowired
    private ProductDetailService productDetailService;

    @PostMapping("/create")
    private ResponseBase createProductDetail(
            @ModelAttribute ProductDetail productDetail,
            @RequestParam(name = "images", required = false) MultipartFile[] images
            ) throws IOException {
        return productDetailService.createProductDetail(images,productDetail);
    }
}
