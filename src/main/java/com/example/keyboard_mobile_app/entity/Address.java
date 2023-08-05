package com.example.keyboard_mobile_app.Entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Data
@Getter
@Setter
public class Address {
    public String addressId;

    public String accountId;

    public HashMap<String, String> address;
}
