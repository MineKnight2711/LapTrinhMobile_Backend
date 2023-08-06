package com.example.keyboard_mobile_app.modules.orderDetail.service;
import com.example.keyboard_mobile_app.entity.OrderDetail;
import com.example.keyboard_mobile_app.modules.ResponseBase;
import com.example.keyboard_mobile_app.modules.order.dto.OrderDto;
import com.example.keyboard_mobile_app.modules.order.dto.ProductOrderDto;
import com.example.keyboard_mobile_app.modules.orderDetail.repository.OrderDetailRepository;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class OrderDetailService {
    @Autowired
    private Firestore firestore;
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    public void createOrderDetail(String orderId, OrderDto orderDto) {
        orderDetailRepository.createOrderDetail(orderId, orderDto);
    }
    public ResponseBase getByOrderId(String orderId) throws ExecutionException, InterruptedException {
        List<OrderDetail> result = orderDetailRepository.getByOrderId(orderId);
        if (!result.isEmpty()) {
            return new ResponseBase(
                    "Get List Order Detail",
                    result
            );
        } else {
            return new ResponseBase(
                    "No Order Detail found!",
                    null
            );
        }
    }
}
