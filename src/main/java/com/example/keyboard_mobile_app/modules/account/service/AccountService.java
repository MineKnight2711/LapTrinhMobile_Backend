package com.example.keyboard_mobile_app.modules.account.service;

import com.example.keyboard_mobile_app.entity.Account;
import com.example.keyboard_mobile_app.modules.account.dto.AccountResponseDto;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutionException;

@Service
public class AccountService {
    @Autowired
    private Firestore firestore;

    public AccountResponseDto getAccountById(String accountId) throws InterruptedException, ExecutionException {
        Firestore firestore = FirestoreClient.getFirestore();

        // Specify the path to the account document in Firestore
        DocumentReference docRef = firestore.collection("users").document(accountId);

        // Fetch the account document
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();
        AccountResponseDto account=new AccountResponseDto();
        if (document.exists()) {
            // Convert the document data to an AccountResponse object
            account = document.toObject(AccountResponseDto.class);
            account.setId(document.getId());
            account.setStatus("Found!!!");
            return account;
        } else {
            // Account with the specified ID not found
            account.setStatus("Không tìm thấy tài khoản!");
            return account;
        }
    }
    public Account create(String id,Account user) {
        CollectionReference collection = firestore.collection("users");
        DocumentReference document = collection.document(id);
        document.set(user);
        return user;
    }

    public Account update(String id, Account updatedUser) throws ExecutionException, InterruptedException {
        DocumentReference document = firestore.collection("user").document(id);
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("name", updatedUser.getFullName());
        userMap.put("age", updatedUser.getBirthday());
        document.update(userMap).get();
        return updatedUser;
    }
}