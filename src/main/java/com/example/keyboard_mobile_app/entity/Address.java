package com.example.keyboard_mobile_app.entity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class Address {
    public String addressId;

    public String accountId;

    public String address;

    public String receiverName;

    public String receiverPhone;

    public boolean defaultAddress;
}
