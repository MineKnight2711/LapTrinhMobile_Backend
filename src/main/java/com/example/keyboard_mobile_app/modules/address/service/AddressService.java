package com.example.keyboard_mobile_app.modules.address.service;

import com.example.keyboard_mobile_app.entity.Address;
import com.example.keyboard_mobile_app.modules.ResponseBase;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {
    @Autowired
    private Firestore firestore;

    public ResponseBase createAddress(String id, Address address) {
        CollectionReference collection = firestore.collection("address");
        DocumentReference document = collection.document(id);
        address.setAddressId(document.getId());
        document.set(address);
        return new ResponseBase(
                "Create address successfully!",
                null
        );
    }
}
