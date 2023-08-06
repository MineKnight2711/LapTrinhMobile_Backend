package com.example.keyboard_mobile_app.modules.address.controller;

import com.example.keyboard_mobile_app.entity.Address;
import com.example.keyboard_mobile_app.modules.ResponseBase;
import com.example.keyboard_mobile_app.modules.address.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/address")
public class AddressController {
    @Autowired
    private AddressService addressService;

    //Get Method
    @GetMapping("/getList/{accountId}")
    private ResponseBase getListByAccountId(
            @PathVariable("accountId") String accountId)
            throws ExecutionException, InterruptedException
    {
        return addressService.getByAccountId(accountId);
    }
    //Post Method
    @PostMapping("/create/{accountId}")
    public ResponseBase createAddress(
            @PathVariable("accountId") String accountId,
            @ModelAttribute Address address
    ) throws ExecutionException, InterruptedException {
        return addressService.createAddress(accountId, address);
    }
    //Put Method
    @PutMapping("/update/{addressId}")
    public ResponseBase updateAddress(
            @PathVariable("addressId") String addressId,
            @ModelAttribute Address updateAddress
            ) throws ExecutionException, InterruptedException {
        return addressService.updateAddress(addressId, updateAddress);
    }
    @PutMapping("/updateV2/{addressId}")
    public ResponseBase updateAddressDatVersion(
            @PathVariable("addressId") String addressId,
            @ModelAttribute Address updateAddress
    ) throws ExecutionException, InterruptedException {
        return addressService.updateAddressDatVersion(addressId, updateAddress);
    }

    @DeleteMapping("/delete/{addressId}")
    public ResponseBase deleteAddress(@PathVariable("addressId") String addressId) throws ExecutionException, InterruptedException {
        return addressService.deleteAddress(addressId);
    }
}
