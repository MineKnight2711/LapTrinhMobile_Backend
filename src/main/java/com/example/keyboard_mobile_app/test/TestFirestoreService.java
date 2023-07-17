package com.example.keyboard_mobile_app.test;

import com.example.keyboard_mobile_app.model.User;
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
    public List<User> getAllUser() throws ExecutionException, InterruptedException {
        List<User> userList = new ArrayList<>();
        ApiFuture<QuerySnapshot> future = firestore.collection("user").get();

        for (QueryDocumentSnapshot document : future.get().getDocuments()) {
            User user = document.toObject(User.class);
            userList.add(user);
        }

        return userList;
    }
    public User add(User user) {
        CollectionReference collection = firestore.collection("user");
        DocumentReference document = collection.document();
        user.setId(document.getId());
        document.set(user);
        return user;
    }
    public User update(String id,User updatedUser) throws ExecutionException, InterruptedException {
        DocumentReference document = firestore.collection("user").document(id);
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("name", updatedUser.getName());
        userMap.put("age", updatedUser.getAge());
        document.update(userMap).get();
        return updatedUser;
    }
}