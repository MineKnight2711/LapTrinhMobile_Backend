package com.example.keyboard_mobile_app.modules.orderDetail.repository;

import com.example.keyboard_mobile_app.entity.OrderDetail;
import com.example.keyboard_mobile_app.modules.order.dto.OrderDto;
import com.example.keyboard_mobile_app.modules.order.dto.ProductOrderDto;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Repository
public class OrderDetailRepository {
    @Autowired
    private Firestore firestore;

    public void createOrderDetail(String orderId, OrderDto orderDto) {
        CollectionReference collection = firestore.collection("orderDetail");
        for (ProductOrderDto productDto: orderDto.lstProduct) {
            DocumentReference document = collection.document();
            OrderDetail result = new OrderDetail();
            result.setOrderId(orderId);
            result.setProductDetailId(productDto.productDetailId);
            result.setQuantity(productDto.quantity);
            document.set(result);
        }
    }

    public List<OrderDetail> getByOrderId(String orderId) throws ExecutionException, InterruptedException {
        Firestore firestore = FirestoreClient.getFirestore();
        CollectionReference colRef = firestore.collection("orderDetail");
        ApiFuture<QuerySnapshot> future = colRef.get();
        QuerySnapshot snapshot = future.get();
        List<OrderDetail> lstOrderDetail = new ArrayList<>();
        for (DocumentSnapshot document : snapshot.getDocuments()) {
            if (document.exists()) {
                OrderDetail orderDetail = document.toObject(OrderDetail.class);
                if(orderDetail.getOrderId().equals(orderId))
                    lstOrderDetail.add(orderDetail);
            }
        }
        return lstOrderDetail;
    }
}
