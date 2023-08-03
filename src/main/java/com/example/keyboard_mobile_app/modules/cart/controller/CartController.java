package com.example.keyboard_mobile_app.modules.cart.controller;

import com.example.keyboard_mobile_app.modules.ResponseBase;
import com.example.keyboard_mobile_app.modules.cart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    //Get Method
    @GetMapping("/{accountId}")
    public ResponseBase getCart(
            @PathVariable("accountId") String accountId
    ) throws ExecutionException, InterruptedException {
        return cartService.getByAccountId(accountId);
    }

    //Post Method
    @PostMapping("/add/{accountId}")
    public ResponseBase addToCart(
            @PathVariable("accountId") String accountId,
            @RequestParam("productDetailId") String productDetailId,
            @RequestParam("quantity") int quantity
    ) throws ExecutionException, InterruptedException {
        return cartService.addToCart(accountId, productDetailId, quantity);
    }
    @PutMapping("/update/{accountId}")
    public ResponseBase updateCart(
            @PathVariable("accountId") String accountId,
            @RequestParam("productDetailId") String productDetailId,
            @RequestParam("quantity") int quantity
    ) throws ExecutionException, InterruptedException {
        return cartService.updateCart(accountId, productDetailId, quantity);
    }
    //Delete Method
    @DeleteMapping("/delete/{accountId}")
    public ResponseBase deleteCart(
            @PathVariable("accountId") String accountId,
            @RequestParam("productDetailId") String productDetailId
    ) throws ExecutionException, InterruptedException {
        return cartService.deleteCart(accountId, productDetailId);
    }
    @DeleteMapping("/clear-cart/{accountId}")
    public ResponseBase clearCart(
            @PathVariable("accountId") String accountId
    ) throws ExecutionException, InterruptedException {
        return cartService.clearCart(accountId);
    }
}
