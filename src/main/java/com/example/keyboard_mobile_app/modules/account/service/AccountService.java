package com.example.keyboard_mobile_app.modules.account.service;

import com.example.keyboard_mobile_app.entity.Account;
import com.example.keyboard_mobile_app.modules.ResponseBase;
import com.example.keyboard_mobile_app.modules.account.dto.AccountResponseDto;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutionException;

@Service
public class AccountService {
    @Autowired
    private Firestore firestore;
    @Autowired
    private FirebaseAuth firebaseAuth;

    public ResponseBase getAccountById(String accountId) throws InterruptedException, ExecutionException {
        Firestore firestore = FirestoreClient.getFirestore();

        // Specify the path to the account document in Firestore
        DocumentReference docRef = firestore.collection("accounts").document(accountId);

        // Fetch the account document
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();
        Account result = new Account();
        if (document.exists()) {
            // Convert the document data to an AccountResponse object
            result = document.toObject(Account.class);
            result.setAccountId(document.getId());
            return new ResponseBase(
                    "User found!",
                    result
            );
        } else {
            // Account with the specified ID not found
            return new ResponseBase(
                    "User not found!",
                    null
            );
        }
    }
    public ResponseBase create(String id,Account user) {
        CollectionReference collection = firestore.collection("accounts");
        DocumentReference document = collection.document(id);
        user.setAccountId(document.getId());
        document.set(user);
        return new ResponseBase(
                "Create account successfully!",
                null
        );
    }

    public ResponseBase updateAccount(String id, Account updatedUser) throws ExecutionException, InterruptedException {
        DocumentReference document = firestore.collection("accounts").document(id);
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("phone", updatedUser.getPhone());
        userMap.put("birthday", updatedUser.getBirthday());
        userMap.put("fullName", updatedUser.getFullName());
        userMap.put("gender", updatedUser.getGender());
        document.update(userMap).get();
        return new ResponseBase(
                "Success",
                null
        );
    }

    public ResponseBase changePassword(String email, String newPassword) {
        try {
            //Check tài khoản
            UserRecord userRecord = firebaseAuth.getUserByEmail(email);
            //tao request thay doi mat khau
            UserRecord.UpdateRequest request = new UserRecord.UpdateRequest(userRecord.getUid())
                    .setPassword(newPassword);
            //cap nhat tai khoan voi mat khau moi
            firebaseAuth.updateUser(request);
            return new ResponseBase(
                    "Success",
                    null
            );
        } catch (FirebaseAuthException e) {
            e.printStackTrace();
            return new ResponseBase(
                    "Đổi mật khẩu không thành công !",
                    null
            );
        }
    }

    public ResponseBase sendPasswordResetLink(String email) {
        try {
            // Check user email
            UserRecord userRecord = firebaseAuth.getUserByEmail(email);
            if(userRecord!=null){
                // Generate the password reset link
                String passwordResetLink = firebaseAuth.generatePasswordResetLink(email);
                return new ResponseBase(
                        passwordResetLink,
                        null
                );
            }
            return new ResponseBase(
                    "UserNotFound",
                    null
            );
        } catch (FirebaseAuthException e) {
            // Handle exceptions (e.g., if the user does not exist)
            e.printStackTrace();
            return new ResponseBase(
                    "SendLinkFail",
                    null
            );
        }
    }
}