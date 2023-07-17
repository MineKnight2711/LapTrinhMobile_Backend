package com.example.keyboard_mobile_app.test.test_controller;

import com.example.keyboard_mobile_app.model.User;
import com.example.keyboard_mobile_app.test.TestFirestoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/test-api")
public class TestController {
    @Autowired
    private TestFirestoreService firestoreService;
    @GetMapping
    public List<User> getAllUser() throws ExecutionException, InterruptedException {
        return firestoreService.getAllUser();
    }
    @PostMapping
    public User addDocumentToFirestore(@ModelAttribute User user) {
        return firestoreService.add(user);
    }
    @PutMapping("/{id}")
    public User updateUser(@PathVariable("id") String id, @ModelAttribute User user) throws ExecutionException, InterruptedException {
        return firestoreService.update(id,user);
    }
}
