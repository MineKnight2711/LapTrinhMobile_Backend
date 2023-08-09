package com.example.keyboard_mobile_app.modules.order.controller;

import com.example.keyboard_mobile_app.modules.ResponseBase;
import com.example.keyboard_mobile_app.modules.order.dto.OrderDto;
import com.example.keyboard_mobile_app.modules.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    //Get Method
    @GetMapping("{accountId}")
    public ResponseBase getByAccountId(
            @PathVariable("accountId") String accountId
    ) throws ExecutionException, InterruptedException {
        return orderService.getOrderByAccountId(accountId);
    }

    @GetMapping("/testGetOrder/{accountId}")
    public ResponseBase getAndFetchOrder(
            @PathVariable("accountId") String accountId
    ) throws ExecutionException, InterruptedException {
        return orderService.getAndFetchOrder(accountId);
    }
    @GetMapping("/status/{accountId}")
    public ResponseBase getByStatus(
            @PathVariable("accountId") String accountId,
            @RequestParam("status") String status
    ) throws ExecutionException, InterruptedException {
        return orderService.getOrderByStatus(accountId,status);
    }

    //Post Method
    @PostMapping("/create")
    public ResponseBase createOrder(
            @RequestBody OrderDto orderDto
    ) throws ExecutionException, InterruptedException {
        return orderService.createOrder(orderDto);
    }

    //Put Method
    @PutMapping("/update/{orderId}")
    public ResponseBase updateStatus(
            @PathVariable("orderId") String orderId,
            @RequestParam("status") String status
    ) throws ExecutionException, InterruptedException {
        return orderService.updateStatusOrder(orderId, status);
    }
}
