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
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class AddressService {
    @Autowired
    private Firestore firestore;

    public ResponseBase createAddress(String accountId, String address) {
        CollectionReference collection = firestore.collection("address");
        DocumentReference document = collection.document();
        Address result = new Address();
        result.setAddressId(document.getId());
        result.setAddress(address);
        result.setAccountId(accountId);
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
}
