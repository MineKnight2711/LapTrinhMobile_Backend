package com.example.keyboard_mobile_app.modules.product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.web.multipart.MultipartFile;

public class ProductDto {
    @JsonProperty("productName")
    public String productName;
    @JsonProperty("unit")
    public String unit;
    @JsonProperty("description")
    public String description;
    @JsonProperty("displayUrl")
    public String displayUrl;
    @JsonProperty("categoryId")
    public String categoryId;
    @JsonProperty("brandId")
    public String brandId;
    @JsonProperty("displayFile")
    public MultipartFile displayFile;
    @JsonProperty("imageFile")
    public MultipartFile[] imageFile;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
    public String getUnit() {
        return unit;
    }
    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDisplayUrl() {
        return displayUrl;
    }

    public void setDisplayUrl(String displayUrl) {
        this.displayUrl = displayUrl;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public MultipartFile getDisplayFile() {
        return displayFile;
    }

    public void setDisplayFile(MultipartFile displayFile) {
        this.displayFile = displayFile;
    }

    public MultipartFile[] getImageFile() {
        return imageFile;
    }

    public void setImageFile(MultipartFile[] imageFile) {
        this.imageFile = imageFile;
    }
}
