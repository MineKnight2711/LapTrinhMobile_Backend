package com.example.keyboard_mobile_app.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class OrderDetail {
    public String product;

    public String order;

    public int quantity;

    public Boolean checkedReview;
}
