package com.example.keyboard_mobile_app.modules;

import org.hibernate.mapping.Any;

import java.util.UUID;

public class ResponseBase {
    public UUID id;
    public String apiVersion;
    public String message;
    public Object data;


    public ResponseBase(String message, Object data) {
        this.id = UUID.randomUUID();
        this.apiVersion = "1.0";
        this.message = message;
        this.data = data;
    }
}
