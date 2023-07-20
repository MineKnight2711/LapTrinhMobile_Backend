package com.example.keyboard_mobile_app.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
@Entity(name="Category")
@Table(name="Category")
public class Category {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long CategoryID;

    @Column(name = "CategoryName",length = 50)
    private String CategoryName;
    @Column(name = "Image",length = 1000)
    private String Image;
//    @OneToMany (mappedBy = "categories", cascade = CascadeType.ALL)
//    @JsonIgnore
//    private List<Dishes> dishes;

}