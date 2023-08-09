package com.example.keyboard_mobile_app.modules.order.dto;

import com.example.keyboard_mobile_app.entity.Product;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class QueryProductDetailDto {
    private String productDetailId;

    private Product product;

    private String color;

    private int price;

    private int quantity;

    private String imageUrl;
}
