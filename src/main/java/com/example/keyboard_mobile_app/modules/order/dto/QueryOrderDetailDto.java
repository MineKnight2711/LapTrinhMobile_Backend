package com.example.keyboard_mobile_app.modules.order.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class QueryOrderDetailDto {
    private String orderId;
    private QueryProductDetailDto productDetail;
    private int quantity;
}
