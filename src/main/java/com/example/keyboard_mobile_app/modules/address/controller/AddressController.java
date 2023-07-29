package com.example.keyboard_mobile_app.modules.address.controller;

import com.example.keyboard_mobile_app.modules.address.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/address")
public class AddressController {
    @Autowired
    private AddressService addressService;

    //Get Method

    //Post Method

}
