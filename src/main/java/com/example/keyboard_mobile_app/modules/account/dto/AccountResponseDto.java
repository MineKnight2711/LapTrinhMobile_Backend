package com.example.keyboard_mobile_app.modules.account.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Data
@Getter
@Setter
public class AccountResponseDto {
    private String id;
    private String imageUrl;
    private String nickname;
    private String fullName;
    private String phone;
    private String email;
    private String gender;
    private Date birthday;
    private String address;
    private String accountType;
    private String status;
}
