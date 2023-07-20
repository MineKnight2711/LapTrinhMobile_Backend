package com.example.keyboard_mobile_app.Entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Getter
@Setter
@Entity(name="Account")
@Table(name="Account")
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long accountID;
    @Column(name = "Password",length = 500)
    private String password;
    @Column(name = "FullName",length = 70)
    private String name;
    @Column(name = "PhoneNumber",length = 20)
    private String phoneNumber;
    @Column(name = "Email",length = 50)
    private String email;
    @Column(name = "Gender",length = 20)
    private String gender;
    @Column(name = "ImageUrl",length = 1000)
    private String imageURL;
    //    @Temporal(TemporalType.TIMESTAMP)
//    @Past(message = "NgaySinh phai nho hon ngay hien tai")
//    @Column(name = "Brithday")
//    private Date Brithday;
    @Column(name = "Address",length = 255)
    private String address;
}
