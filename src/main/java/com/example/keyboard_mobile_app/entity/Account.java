package com.example.keyboard_mobile_app.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Past;
import java.util.Date;

@Data
@Getter
@Setter
public class Account {
    private String fullName;
    @Temporal(TemporalType.TIMESTAMP)
    @Past(message = "Ngay Sinh phai nho hon ngay hien tai")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;
    private String accountType;
    private String email;
    private String gender;
    private String address;
    private String nickname;
    private String imageUrl;
    private String phone;
}