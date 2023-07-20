package com.example.keyboard_mobile_app.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Data
@Getter
@Setter
@Entity(name="AccountType")
@Table(name="AccountType")
public class AccountType {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long accountTypeID;
    @Column(name = "AccountTypeName",length = 50)
    private String accountTypeName;

    @OneToMany (mappedBy = "accountTypes", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Account> account;

}
