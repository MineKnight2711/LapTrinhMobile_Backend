package com.example.keyboard_mobile_app.modules.product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.web.multipart.MultipartFile;

public class ProductDto {
    @JsonProperty("productName")
    public String name;
    @JsonProperty("price")
    public int price;
    @JsonProperty("quantity")
    public int quantity;
    @JsonProperty("unit")
    public String unit;
    @JsonProperty("description")
    public String description;
    @JsonProperty("displayUrl")
    public String displayUrl;
    @JsonProperty("imageUrl")
    public String imageUrl;
    @JsonProperty("categoryId")
    public Long categoryId;
    @JsonProperty("brandId")
    public String brandId;
    @JsonProperty("displayFile")
    public MultipartFile displayFile;
    @JsonProperty("imageFile")
    public MultipartFile[] imageFile;
}
