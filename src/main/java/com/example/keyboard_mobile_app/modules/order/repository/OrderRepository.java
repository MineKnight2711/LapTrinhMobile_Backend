package com.example.keyboard_mobile_app.modules.order.repository;

import com.example.keyboard_mobile_app.entity.Order;
import com.example.keyboard_mobile_app.modules.order.dto.OrderDto;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Repository
public class OrderRepository {
    @Autowired
    private Firestore firestore;

    public List<Order> getOrderByAccountId(String accountId) throws ExecutionException, InterruptedException {
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
        return lstOrder;
    }

    public List<Order> getOrderByStatus(String accountId, String status) throws ExecutionException, InterruptedException {
        List<Order> lstOrder = getOrderByAccountId(accountId);
        for (Order order: lstOrder) {
            if (!order.getStatus().equals(status)) {
                lstOrder.remove(order);
            }
        }
        return lstOrder;
    }

    public Order createOrder(OrderDto orderDto) {
        CollectionReference collection = firestore.collection("order");
        DocumentReference document = collection.document();
        Order result = new Order();
        result.setOrderId(document.getId());
        result.setOrderDate(java.sql.Date.valueOf(LocalDate.now()));
        result.setStatus("Chờ xác nhận");
        result.setReceiverInfo(orderDto.addressInfo);
        result.setAccountId(orderDto.accountId);
        document.set(result);
        return result;
    }
}
