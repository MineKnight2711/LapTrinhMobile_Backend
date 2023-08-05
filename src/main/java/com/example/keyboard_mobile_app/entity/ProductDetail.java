package com.example.keyboard_mobile_app.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ProductDetail {
    private String productDetailId;

    private String productId;

    private String color;

    private int price;

    private int quantity;

    private String imageUrl;
}
