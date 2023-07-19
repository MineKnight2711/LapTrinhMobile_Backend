package com.example.keyboard_mobile_app.test;

import com.example.keyboard_mobile_app.entity.Account;
import com.example.keyboard_mobile_app.test.test_repo.TestRepository;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class TestFirestoreService {
    @Autowired
    private Firestore firestore;
    @Autowired
    private TestRepository testRepository;
    public List<Account> getUserByName(String name) throws ExecutionException, InterruptedException {
        List<Account> users = testRepository.findByUsername(name);
        if (!users.isEmpty()) {
            return users;
        } else {
            return null;
        }
    }
    public List<Account> getAllUser() throws ExecutionException, InterruptedException {
        List<Account> userList = new ArrayList<>();
        ApiFuture<QuerySnapshot> future = firestore.collection("user").get();

        for (QueryDocumentSnapshot document : future.get().getDocuments()) {
            Account user = document.toObject(Account.class);
            userList.add(user);
        }

        return userList;
    }
    public Account add(Account user) {
        CollectionReference collection = firestore.collection("user");
        DocumentReference document = collection.document();
        user.setId(document.getId());
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