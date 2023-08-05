package com.example.keyboard_mobile_app.modules.order.service;
import com.example.keyboard_mobile_app.entity.Order;
import com.example.keyboard_mobile_app.modules.ResponseBase;
import com.example.keyboard_mobile_app.modules.order.dto.OrderDto;
import com.example.keyboard_mobile_app.modules.order.dto.ProductOrderDto;
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
    private FirebaseAuth firebaseAuth;
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private ProductDetailService productDetailService;

    public ResponseBase getOrderByAccountId(String accountId) throws ExecutionException, InterruptedException {
        Firestore firestore = FirestoreClient.getFirestore();
        CollectionReference colRef = firestore.collection("order");
        ApiFuture<QuerySnapshot> future = colRef.get();
        QuerySnapshot snapshot = future.get();
        List<Order> lstOrder = new ArrayList<>();
        for (DocumentSnapshot document : snapshot.getDocuments()) {
            if (document.exists()) {
                Order order = document.toObject(Order.class);
                order.setOrderId(document.getId());
                if(order.getAccountId().equals(accountId))
                    lstOrder.add(order);
            }
        }
        if (!lstOrder.isEmpty()) {
            return new ResponseBase(
                    "Get List Order",
                    lstOrder
            );
        } else {
            return new ResponseBase(
                    "No Order found!",
                    null
            );
        }
    }
    public ResponseBase getOrderByStatus(String accountId, String status) throws ExecutionException, InterruptedException {
        Firestore firestore = FirestoreClient.getFirestore();
        CollectionReference colRef = firestore.collection("order");
        ApiFuture<QuerySnapshot> future = colRef.get();
        QuerySnapshot snapshot = future.get();
        List<Order> lstOrder = new ArrayList<>();
        for (DocumentSnapshot document : snapshot.getDocuments()) {
            if (document.exists()) {
                Order order = document.toObject(Order.class);
                order.setOrderId(document.getId());
                if(order.getStatus().equals(status))
                    lstOrder.add(order);
            }
        }
        if (!lstOrder.isEmpty()) {
            return new ResponseBase(
                    "Get List Order",
                    lstOrder
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
        CollectionReference collection = firestore.collection("order");
        DocumentReference document = collection.document();
        Order result = new Order();
        result.setOrderId(document.getId());
        result.setOrderDate(java.sql.Date.valueOf(LocalDate.now()));
        result.setStatus("Chờ xác nhận");
        result.setReceiverInfo(orderDto.addressInfo);
        result.setAccountId(orderDto.accountId);
        document.set(result);
        orderDetailService.createOrderDetail(result.orderId, orderDto);
        return new ResponseBase(
                "Success",
                null
        );
    }
}
