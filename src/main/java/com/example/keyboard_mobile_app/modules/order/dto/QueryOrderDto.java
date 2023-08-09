package com.example.keyboard_mobile_app.modules.order.dto;



import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
@Getter
@Setter
public class QueryOrderDto {
    private String orderId;
    private String accountId;
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date deliveryDate;
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date orderDate;
    private String receiverInfo;
    private String status;
    private List<QueryOrderDetailDto> listOrderDetail;
}
