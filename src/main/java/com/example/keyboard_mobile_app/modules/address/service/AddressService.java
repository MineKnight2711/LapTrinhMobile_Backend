package com.example.keyboard_mobile_app.modules.address.service;

import com.example.keyboard_mobile_app.entity.Address;
import com.example.keyboard_mobile_app.entity.Product;
import com.example.keyboard_mobile_app.modules.ResponseBase;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
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

    public ResponseBase createAddress(String accountId, Address address) {
        CollectionReference collection = firestore.collection("address");
        DocumentReference document = collection.document();
        Address result = new Address();
        result.setAddressId(document.getId());
        result.setAddress(address.getAddress());
        result.setAccountId(accountId);
        result.setReceiverName(address.getReceiverName());
        result.setReceiverPhone(address.getReceiverPhone());
        result.setDefaultAddress(address.defaultAddress);
        document.set(result);
        return new ResponseBase(
                "Create address successfully!",
                null
        );
    }
    public ResponseBase getByAccountId(String accountId) throws ExecutionException, InterruptedException {
        Firestore firestore = FirestoreClient.getFirestore();
        CollectionReference colRef = firestore.collection("address");
        ApiFuture<QuerySnapshot> future = colRef.get();
        QuerySnapshot snapshot = future.get();
        List<Address> lstAddress = new ArrayList<>();
        for (DocumentSnapshot document : snapshot.getDocuments()) {
            if (document.exists()) {
                Address address = document.toObject(Address.class);
                address.setAddressId(document.getId());
                if(address.getAccountId().equals(accountId))
                    lstAddress.add(address);
            }
        }
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
        DocumentReference document = firestore.collection("address").document(addressId);
        Map<String, Object> addressMap = new HashMap<>();
        addressMap.put("address", updateAddress.address);
        addressMap.put("receiverName", updateAddress.receiverName);
        addressMap.put("receiverPhone", updateAddress.receiverPhone);
        addressMap.put("defaultAdress", updateAddress.defaultAddress);
        document.update(addressMap).get();
        return new ResponseBase(
                "Update Address Successfully!",
                addressMap
        );
    }
}
