package com.example.keyboard_mobile_app.modules.productDetail.controller;

import com.example.keyboard_mobile_app.entity.ProductDetail;
import com.example.keyboard_mobile_app.modules.ResponseBase;
import com.example.keyboard_mobile_app.modules.productDetail.service.ProductDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/productDetail")
public class ProductDetailController {
    @Autowired
    private ProductDetailService productDetailService;

    //Get Method
    @GetMapping()
    public ResponseBase getListProductDetail() throws ExecutionException, InterruptedException {
        return productDetailService.getListProduct();
    }
    @GetMapping("/getById/{id}")
    public ResponseBase getById(@PathVariable("id") String id) throws ExecutionException, InterruptedException {
        return productDetailService.getById(id);
    }
    @GetMapping("/listProduct/{productId}")
    public ResponseBase getListByProductId(
            @PathVariable("productId") String productId
    ) throws ExecutionException, InterruptedException {
        return productDetailService.getListByProductId(productId);
    }
    @DeleteMapping("/{detailId}")
    public ResponseBase deleteDetail(
            @PathVariable("detailId") String detailId
    ) throws ExecutionException, InterruptedException {
        return productDetailService.deleteDetail(detailId);
    }
    //Post Method
    @PostMapping("/create")
    private ResponseBase createProductDetail(
            @ModelAttribute ProductDetail productDetail,
            @RequestParam(name = "images", required = false) MultipartFile[] images
            ) throws IOException {
        return productDetailService.createProductDetail(images,productDetail);
    }
}
