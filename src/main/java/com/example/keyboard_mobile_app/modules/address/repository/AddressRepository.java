package com.example.keyboard_mobile_app.modules.address.repository;
import com.example.keyboard_mobile_app.entity.Address;
import com.example.keyboard_mobile_app.modules.ResponseBase;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Repository
public class AddressRepository {
    @Autowired
    private Firestore firestore;

    public Address createAddress(String accountId, Address address) throws ExecutionException, InterruptedException {
        CollectionReference collection = firestore.collection("address");
        DocumentReference document = collection.document();
        if(address.isDefaultAddress()) {
            setNonDefault(accountId);
        }
        Address result = new Address();
        result.setAddressId(document.getId());
        result.setAddress(address.getAddress());
        result.setAccountId(accountId);
        result.setReceiverName(address.getReceiverName());
        result.setReceiverPhone(address.getReceiverPhone());
        result.setDefaultAddress(address.isDefaultAddress());
        document.set(result);

        return result;
    }

    public List<Address> getAddByAccountId(String accountId) throws ExecutionException, InterruptedException {
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
        return lstAddress;
    }
    public Address getByAddressId(String addressId) throws ExecutionException, InterruptedException {
        Firestore firestore = FirestoreClient.getFirestore();
        DocumentReference docRef = firestore.collection("address").document(addressId);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();
        Address result = new Address();
        if (document.exists()) {
            result = document.toObject(Address.class);
        }
        return result;
    }
    public void updateAddress(String addressId, Address updateAddress) throws ExecutionException, InterruptedException {
        DocumentReference document = firestore.collection("address").document(addressId);
        Map<String, Object> addressMap = new HashMap<>();
        Address temp = getByAddressId(document.getId());
        if(updateAddress.isDefaultAddress()) {
            setNonDefault(temp.getAccountId());
        }
        addressMap.put("address", updateAddress.getAddress());
        addressMap.put("receiverName", updateAddress.getReceiverName());
        addressMap.put("receiverPhone", updateAddress.getReceiverPhone());
        addressMap.put("defaultAddress", updateAddress.isDefaultAddress());
        document.update(addressMap).get();
    }
    public void setNonDefault(String accountId) throws ExecutionException, InterruptedException {
        List<Address> lstAddress = getAddByAccountId(accountId);
        if(lstAddress != null) {
            for (Address address: lstAddress) {
                if(address.isDefaultAddress()) {
                    Address updateAddress = address;
                    updateAddress.setDefaultAddress(false);
                    DocumentReference document = firestore.collection("address").document(updateAddress.getAddressId());
                    Map<String, Object> addressMap = new HashMap<>();
                    addressMap.put("address", updateAddress.getAddress());
                    addressMap.put("receiverName", updateAddress.getReceiverName());
                    addressMap.put("receiverPhone", updateAddress.getReceiverPhone());
                    addressMap.put("defaultAddress", updateAddress.isDefaultAddress());
                    document.update(addressMap).get();
                    break;
                }
            }
        }
    }
}
