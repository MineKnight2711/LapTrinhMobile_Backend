package com.example.keyboard_mobile_app.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Date;

@Data
@Getter
@Setter
public class Review {
    private String reviewId;

    private String accountId;

    private String productId;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateReview;

    @Max(5)
    @Min(1)
    private double star;

    private String comment;
}
