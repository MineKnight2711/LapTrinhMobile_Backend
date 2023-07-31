package com.example.keyboard_mobile_app.modules.orderDetail.controller;

import com.example.keyboard_mobile_app.modules.ResponseBase;
import com.example.keyboard_mobile_app.modules.orderDetail.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/orderDetail")
public class OrderDetailController {
    @Autowired
    private OrderDetailService orderDetailService;

    //Get Method
    @GetMapping("/{orderId}")
    private ResponseBase getByOrderId(
            @PathVariable("orderId") String orderId
    ) throws ExecutionException, InterruptedException {
        return orderDetailService.getByOrderId(orderId);
    }
}
