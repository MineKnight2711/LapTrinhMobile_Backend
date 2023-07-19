package com.example.keyboard_mobile_app.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.Past;
import java.util.Date;

@Data
@Getter
@Setter
public class Account {
    private String id;
    private String fullName;
    @Temporal(TemporalType.TIMESTAMP)
    @Past(message = "Ngay Sinh phai nho hon ngay hien tai")
    private Date birthday;
    private String accountType;
    private String email;
    private String gender;
    private String address;
    private String nickname;
    private String password;
    private String imageUrl;
    private String phone;
}