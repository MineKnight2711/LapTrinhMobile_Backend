package com.example.keyboard_mobile_app.modules.address.service;

import com.example.keyboard_mobile_app.entity.Address;
import com.example.keyboard_mobile_app.modules.ResponseBase;
import com.example.keyboard_mobile_app.modules.address.repository.AddressRepository;
import com.google.cloud.firestore.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class AddressService {
    @Autowired
    private Firestore firestore;
    @Autowired
    private AddressRepository addressRepository;

    public ResponseBase createAddress(String accountId, Address address) throws ExecutionException, InterruptedException {
        Address result = addressRepository.createAddress(accountId, address);
        return new ResponseBase(
                "Create address successfully!",
                result
        );
    }
    public ResponseBase getByAccountId(String accountId) throws ExecutionException, InterruptedException {
        List<Address> lstAddress = addressRepository.getAddByAccountId(accountId);
        if (!lstAddress.isEmpty()) {
            return new ResponseBase(
                    "Get List Address",
                    lstAddress
            );
        } else {
            return new ResponseBase(
                    "No Address found!",
                    null
            );
        }
    }
    public ResponseBase updateAddress(String addressId, Address updateAddress) throws ExecutionException, InterruptedException {
        addressRepository.updateAddress(addressId, updateAddress);
        return new ResponseBase(
                "Update Address Successfully!",
                null
        );
    }
    public ResponseBase updateAddressDatVersion(String addressId, Address updateAddress) throws ExecutionException, InterruptedException {
        DocumentReference document = firestore.collection("address").document(addressId);
        Map<String, Object> addressMap = new HashMap<>();
        addressMap.put("address", updateAddress.getAddress());
        addressMap.put("receiverName", updateAddress.getReceiverName());
        addressMap.put("receiverPhone", updateAddress.getReceiverPhone());
        addressMap.put("defaultAddress", updateAddress.isDefaultAddress());

        ResponseBase newResponebase = new ResponseBase(
                "",
                null
        );


        // Query cac dia chia khac cua user
        CollectionReference addressCollection = firestore.collection("address");
        Query query = addressCollection.whereEqualTo("accountId", updateAddress.getAccountId())
                .whereEqualTo("defaultAddress", true);
        QuerySnapshot querySnapshot = query.get().get();

        // Neu defaultAddress duoc cap nhat thanh true
        if (updateAddress.isDefaultAddress()) {
            // Cap nhat dia chi hien tai
            document.update(addressMap).get();

            //Lặp qua cac Document để check  defaultAddress nào true và sửa lại thành false
            for (QueryDocumentSnapshot snapshot : querySnapshot.getDocuments()) {
                String otherAddressId = snapshot.getId();
                if (!otherAddressId.equals(addressId)) {
                    DocumentReference otherDocument = addressCollection.document(otherAddressId);
                    otherDocument.update("defaultAddress", false);
                }
            }

            newResponebase.message="Update Address Successfully!";
            newResponebase.data=addressMap;
        }
        else if(querySnapshot.size()==1 && !updateAddress.isDefaultAddress()){
            //Kiểm tra truong hop nguoi dung set nguoc ve false cua dia chi mac dinh duy nhat
            newResponebase.message="There must be at least 1 default address!";
        }
        return newResponebase;
    }


    public ResponseBase deleteAddress(String addressId) throws ExecutionException, InterruptedException {
        String result=addressRepository.deleteAddress(addressId);
        switch (result) {
            case "DeleteSuccess":
                return new ResponseBase("DeleteSuccess",null);
            case "IsDefault":
                return new ResponseBase("IsDefault",null);
            case "AddressNotFound":
                return new ResponseBase("AddressNotFound",null);
            default:
                return new ResponseBase("SomethingWrong",null);
        }
    }
}
