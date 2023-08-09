package com.example.keyboard_mobile_app.modules.order.repository;

import com.example.keyboard_mobile_app.entity.Order;
import com.example.keyboard_mobile_app.entity.ProductDetail;
import com.example.keyboard_mobile_app.modules.order.dto.OrderDto;
import com.example.keyboard_mobile_app.modules.order.dto.QueryOrderDetailDto;
import com.example.keyboard_mobile_app.modules.order.dto.QueryOrderDto;
import com.example.keyboard_mobile_app.modules.order.dto.QueryProductDetailDto;
import com.example.keyboard_mobile_app.modules.productDetail.repository.ProductDetailRepository;
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
    @Autowired
    private ProductDetailRepository productDetailRepository;


    public List<QueryOrderDto> getOrderByStatus(String accountId, String status) throws ExecutionException, InterruptedException {
        List<QueryOrderDto> lstOrder = getOrdersByAccountId(accountId);
        for (QueryOrderDto order: lstOrder) {
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
        result.setOrderDate(new java.sql.Timestamp(System.currentTimeMillis()));
        result.setStatus("Chờ xác nhận");
        result.setReceiverInfo(orderDto.receiverInfo);
        result.setAccountId(orderDto.accountId);
        document.set(result);
        return result;
    }
    // code này ghê lắm đừng xem :))
    //Lay danh sách đơn hàng theo accountId trước
    public List<QueryOrderDto> getOrdersByAccountId(String accountId) throws ExecutionException, InterruptedException {
        Firestore firestore = FirestoreClient.getFirestore();
        CollectionReference colRef = firestore.collection("order");
        ApiFuture<QuerySnapshot> future = colRef.get();
        QuerySnapshot snapshot = future.get();

        List<QueryOrderDto> lstQueryOrderDto = new ArrayList<>();

        for (DocumentSnapshot document : snapshot.getDocuments()) {
            if (document.exists()) {
                QueryOrderDto queryOrderDto = document.toObject(QueryOrderDto.class);
                queryOrderDto.setOrderId(document.getId());

                if (queryOrderDto.getAccountId().equals(accountId)) {
                    lstQueryOrderDto.add(queryOrderDto);
                }
            }
        }

        return lstQueryOrderDto;
    }


//    public List<Order> getOrderByAccountId(String accountId) throws ExecutionException, InterruptedException {
//        Firestore firestore = FirestoreClient.getFirestore();
//        CollectionReference colRef = firestore.collection("order");
//        ApiFuture<QuerySnapshot> future = colRef.get();
//        QuerySnapshot snapshot = future.get();
//        List<Order> lstOrder = new ArrayList<>();
//        for (DocumentSnapshot document : snapshot.getDocuments()) {
//            if (document.exists()) {
//                Order order = document.toObject(Order.class);
//                order.setOrderId(document.getId());
//                if(order.getAccountId().equals(accountId))
//                    lstOrder.add(order);
//            }
//        }
//        return lstOrder;
//    }

    //Sau đó dùng kết quả tìm thấy để fetch tiếp orderdetail
    public List<QueryOrderDto> getOrdersWithDetailsByAccountId(String accountId) throws ExecutionException, InterruptedException {
        List<QueryOrderDto> ordersWithDetails = getOrdersByAccountId(accountId);

        Firestore firestore = FirestoreClient.getFirestore();

        for (QueryOrderDto order : ordersWithDetails) {
            List<QueryOrderDetailDto> orderDetails = new ArrayList<>();
            CollectionReference orderDetailColRef = firestore.collection("orderDetail");
            Query orderDetailQuery = orderDetailColRef.whereEqualTo("orderId", order.getOrderId());
            ApiFuture<QuerySnapshot> orderDetailQueryFuture = orderDetailQuery.get();
            QuerySnapshot orderDetailSnapshot = orderDetailQueryFuture.get();

            for (DocumentSnapshot orderDetailDocument : orderDetailSnapshot.getDocuments()) {
                if (orderDetailDocument.exists()) {
                    QueryOrderDetailDto orderDetail = orderDetailDocument.toObject(QueryOrderDetailDto.class);
                    QueryProductDetailDto productDetail =  productDetailRepository.queryDetailById(orderDetailDocument.getString("productDetailId"));
                    orderDetail.setProductDetail(productDetail);
                    orderDetails.add(orderDetail);
                }
            }

            order.setListOrderDetail(orderDetails);
        }
        return ordersWithDetails;
    }
}
