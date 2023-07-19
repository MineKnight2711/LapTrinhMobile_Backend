package com.example.keyboard_mobile_app.test.test_repo;

import com.example.keyboard_mobile_app.entity.Account;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Repository
public class TestRepository {
    @Autowired
    private Firestore firestore;

    public List<Account> findByUsername(String username) throws ExecutionException, InterruptedException {
        CollectionReference usersCollection = firestore.collection("user");
        Query query = usersCollection.whereEqualTo("name", username);
        ApiFuture<QuerySnapshot> querySnapshot = query.get();

        List<Account> users = new ArrayList<>();
        for (QueryDocumentSnapshot document : querySnapshot.get().getDocuments()) {
            Account user = document.toObject(Account.class);
            users.add(user);
        }

        return users;
    }
}