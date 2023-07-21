package com.example.keyboard_mobile_app.modules.account.service;

import com.example.keyboard_mobile_app.entity.Account;
import com.example.keyboard_mobile_app.modules.account.repository.AccountRepository;

import com.example.keyboard_mobile_app.utils.DataConvertService;
import com.google.cloud.firestore.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutionException;

@Service
public class AccountService {
    @Autowired
    private Firestore firestore;
    @Autowired
    private AccountRepository accountRepository;


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