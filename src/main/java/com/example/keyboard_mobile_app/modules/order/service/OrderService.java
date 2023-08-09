package com.example.keyboard_mobile_app.modules.order.service;
import com.example.keyboard_mobile_app.entity.Order;
import com.example.keyboard_mobile_app.modules.ResponseBase;
import com.example.keyboard_mobile_app.modules.order.dto.OrderDto;
import com.example.keyboard_mobile_app.modules.order.dto.ProductOrderDto;
import com.example.keyboard_mobile_app.modules.order.dto.QueryOrderDto;
import com.example.keyboard_mobile_app.modules.order.repository.OrderRepository;
import com.example.keyboard_mobile_app.modules.orderDetail.service.OrderDetailService;
import com.example.keyboard_mobile_app.modules.productDetail.service.ProductDetailService;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class OrderService {
    @Autowired
    private Firestore firestore;
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private ProductDetailService productDetailService;
    @Autowired
    private OrderRepository orderRepository;

    public ResponseBase getOrderByAccountId(String accountId) throws ExecutionException, InterruptedException {
        List<QueryOrderDto> result = orderRepository.getOrdersByAccountId(accountId);
        if (!result.isEmpty()) {
            return new ResponseBase(
                    "Get List Order",
                    result
            );
        } else {
            return new ResponseBase(
                    "No Order found!",
                    null
            );
        }
    }

    public ResponseBase getAndFetchOrder(String accountId) throws ExecutionException, InterruptedException {
        List<QueryOrderDto> result = orderRepository.getOrdersWithDetailsByAccountId(accountId);
        if (!result.isEmpty()) {
            return new ResponseBase(
                    "Get List Successfully",
                    result
            );
        } else {
            return new ResponseBase(
                    "No Order found!",
                    null
            );
        }
    }

    public ResponseBase getOrderByStatus(String accountId, String status) throws ExecutionException, InterruptedException {
        List<QueryOrderDto> result = orderRepository.getOrderByStatus(accountId, status);
        if (!result.isEmpty()) {
            return new ResponseBase(
                    "Get List Order",
                    result
            );
        } else {
            return new ResponseBase(
                    "No Order found!",
                    null
            );
        }
    }

    public ResponseBase createOrder(
            OrderDto orderDto
    ) throws ExecutionException, InterruptedException {
        for (ProductOrderDto productOrderDto: orderDto.lstProduct
             ) {
            if(!productDetailService.checkSl(productOrderDto.productDetailId, productOrderDto.quantity))
                return new ResponseBase(
                        "Out of quantity",
                        null
                );
        }
        Order result = orderRepository.createOrder(orderDto);
        orderDetailService.createOrderDetail(result.orderId, orderDto);
        return new ResponseBase(
                "Success",
                result
        );
    }
    public ResponseBase updateStatusOrder(String orderId, String status) throws ExecutionException, InterruptedException {
        orderRepository.changeStatusOrder(orderId, status);
        return new ResponseBase(
                "Success",
                null
        );
    }

}
