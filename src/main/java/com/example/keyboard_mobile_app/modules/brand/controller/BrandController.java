package com.example.keyboard_mobile_app.modules.brand.controller;

import com.example.keyboard_mobile_app.entity.Account;
import com.example.keyboard_mobile_app.entity.Brand;
import com.example.keyboard_mobile_app.modules.ResponseBase;
import com.example.keyboard_mobile_app.modules.account.service.AccountService;
import com.example.keyboard_mobile_app.modules.brand.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/brand")
public class BrandController {
    @Autowired
    private BrandService brandService;

    //Get Method
    @GetMapping()
    public ResponseBase getList() throws ExecutionException, InterruptedException {
        return brandService.getList();
    }

    //Post Method
    @PostMapping("/create")
    public ResponseBase createBrand(
            @RequestParam String brandName
    ){
        return brandService.createBrand(brandName);
    }

    //Put Method
    @PutMapping("/update/{id}")
    public ResponseBase updateBrand(
            @PathVariable("id") String id,
            @RequestParam("brandName") String brandName
    ) throws ExecutionException, InterruptedException {
        return brandService.updateBrand(id, brandName);
    }
}
