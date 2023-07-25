package com.example.keyboard_mobile_app.modules.account.controller;

import com.example.keyboard_mobile_app.entity.Account;
import com.example.keyboard_mobile_app.modules.account.dto.AccountResponseDto;
import com.example.keyboard_mobile_app.modules.account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/account")
public class AccountController {
    @Autowired
    private AccountService accountService;
    @GetMapping("/{id}")
    public AccountResponseDto getAccountById(@PathVariable("id") String id) throws ExecutionException, InterruptedException {
        return accountService.getAccountById(id);
    }
    @PostMapping("/{id}")
    public Account createNewAccount(
            @PathVariable("id") String id,
            @ModelAttribute Account user)
    {
        return accountService.create(id,user);
    }
    @PutMapping("/{email}")
    public String changePassword(@PathVariable("email") String email,@RequestParam("newPassword") String newPassword)
    {
        return accountService.changePassword(email,newPassword);
    }
}
