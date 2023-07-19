package com.example.keyboard_mobile_app.test.test_controller;

import com.example.keyboard_mobile_app.entity.Account;
import com.example.keyboard_mobile_app.test.TestFirestoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/test-api")
public class TestController {
    @Autowired
    private TestFirestoreService firestoreService;
    @GetMapping("/{name}")
    public List<Account> getUserByName(@PathVariable("name") String name) throws ExecutionException, InterruptedException {
        return firestoreService.getUserByName(name);
    }
    @GetMapping
    public List<Account> getAllUser() throws ExecutionException, InterruptedException {
        return firestoreService.getAllUser();
    }
    @PostMapping
    public Account addDocumentToFirestore(@ModelAttribute Account user) {
        return firestoreService.add(user);
    }
    @PutMapping("/{id}")
    public Account updateUser(@PathVariable("id") String id, @ModelAttribute Account user) throws ExecutionException, InterruptedException {
        return firestoreService.update(id,user);
    }
}
